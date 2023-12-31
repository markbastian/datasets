(ns datasets.bgg
  "Code to process and load the boardgamegeek dataset from
  https://www.kaggle.com/datasets/andrewmvd/board-games"
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader :separator \;)
          headers (map (fn [h] (keyword (str/replace h #"[\s\uFEFF]+" ""))) f)]
      (mapv (partial zipmap headers) rows))))

(def db {:dbtype "sqlite" :dbname "identifier.sqlite"})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["DROP TABLE IF EXISTS BGG"])

(jdbc/execute! ds [(slurp "migrations/001_Create_BGG.sql")])

(doall
  (->> (load-csv "local/bgg_dataset.csv")
       (filter (comp seq :ID))
       (partition-all 5000)
       (map #(sql/insert-multi! ds "BGG" %))))

(println
  (jdbc/execute! ds ["select COUNT(*) from BGG"]))