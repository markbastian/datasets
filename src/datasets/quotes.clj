(ns datasets.quotes
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader :quote \")
          headers (map (fn [h] (keyword (str/replace h #"[\s\uFEFF]+" ""))) f)]
      (mapv (partial zipmap headers) rows))))

(def db {:dbtype "sqlite" :dbname "identifier.sqlite"})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["DROP TABLE IF EXISTS Quotes"])

(jdbc/execute! ds [(slurp "migrations/002_Create_Quotes.sql")])

(doall
  (->> (load-csv "local/quotes.csv")
       (partition-all 500)
       (map #(sql/insert-multi! ds "Quotes" %))))

(println
  (jdbc/execute! ds ["select COUNT(*) from Quotes"]))