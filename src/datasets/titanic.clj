(ns datasets.titanic
  "Code to process and load the titanic dataset from
  https://www.kaggle.com/datasets/yasserh/titanic-dataset"
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader)
          headers (map (fn [h] (keyword (str/replace h #"[\s\uFEFF]+" ""))) f)]
      (mapv (partial zipmap headers) rows))))

(def db {:dbtype "sqlite" :dbname "identifier.sqlite"})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["DROP TABLE IF EXISTS Titanic"])

(jdbc/execute! ds [(slurp "migrations/000_Create_Titanic.sql")])

(doall
  (->> (load-csv "local/Titanic-Dataset.csv")
       (partition-all 500)
       (map #(sql/insert-multi! ds "Titanic" %))))

(println
  (jdbc/execute! ds ["select COUNT(*) from Titanic"]))