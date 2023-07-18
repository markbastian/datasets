(ns datasets.core
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [marge.core :as marge]))

(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader :separator \;)
          headers f]
      (mapv (partial zipmap headers) rows))))

(defn load-cols [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader :separator \;)
          headers (mapv vector f)]
      (loop [[row & r] rows acc headers]
        (if row
          (recur r (mapv (fn [a r] (conj a r)) acc row))
          acc)))))

(defn load-quotes []
  (with-open [reader (io/reader "quotes.csv")]
    (let [[f & rows] (csv/read-csv reader)
          headers (map keyword f)]
      (->> rows
           (map (partial zipmap headers))
           (mapv (fn [row] (update row :category (comp set #(str/split % #",\s*")))))))))

(comment
  (->> (load-quotes)
       (filter (comp #{"Andy Griffith"} :author))))

(defn sample [file]
  (let [cols (load-cols file)
        h    (map first cols)
        r    (map rest cols)]
    (format
      "%s\n%s"
      (str/replace (.getName file) #"\.csv" "")
      (marge/markdown
        [:table
         (interleave h (map #(vec (take 3 %)) r))]))))

(comment

  (let [files (file-seq (io/file "local/baseball_databank"))]
    (->> (for [file files
               :when (.isFile file)
               :let [cols (load-cols file)
                     h    (map first cols)
                     r    (map rest cols)]]
           (format
             "%s\n%s"
             (str/replace (.getName file) #"\.csv" "")
             (marge/markdown
               [:table
                (interleave h (map #(vec (take 3 %)) r))])))
         (str/join "\n")
         println))

  (println
    (sample (io/file "local/Titanic-Dataset.csv")))

  (println
    (sample (io/file "local/bgg_dataset.csv")))

  (take 3 (load-csv "local/bgg_dataset.csv"))
  )
