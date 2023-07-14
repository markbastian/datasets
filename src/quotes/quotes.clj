(ns quotes.quotes
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def quotes
  (with-open [reader (io/reader "quotes.csv")]
    (let [[f & rows] (csv/read-csv reader)
          headers (map keyword f)]
      (->> rows
           (map (partial zipmap headers))
           (mapv (fn [row] (update row :category (comp set #(str/split % #",\s*")))))))))

(->> quotes
     (filter (comp #{"Andy Griffith"} :author)))