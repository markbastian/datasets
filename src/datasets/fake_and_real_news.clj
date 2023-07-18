(ns datasets.fake-and-real-news
  "Code to process and load the fake and real news dataset from
  https://www.kaggle.com/datasets/clmentbisaillon/fake-and-real-news-dataset"
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader :quote \")
          headers (map keyword f)]
      (mapv (partial zipmap headers) rows))))

(def db {:dbtype "sqlite" :dbname "identifier.sqlite"})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["DROP TABLE IF EXISTS FakeAndRealNews"])

(jdbc/execute! ds [(slurp "migrations/027_Create_FakeAndRealNews.sql")])

(doall
  (->> (load-csv "local/Fake.csv")
       (map (fn [m] (assoc m :Fake true)))
       (partition-all 500)
       (map #(sql/insert-multi! ds "FakeAndRealNews" %))))

(doall
  (->> (load-csv "local/True.csv")
       (map (fn [m] (assoc m :Fake false)))
       (partition-all 500)
       (map #(sql/insert-multi! ds "FakeAndRealNews" %))))

(println
  (jdbc/execute! ds ["select COUNT(*) from FakeAndRealNews"]))