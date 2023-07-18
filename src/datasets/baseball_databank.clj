(ns datasets.baseball-databank
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [marge.core :as marge]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn load-cols [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader)
          headers (mapv vector f)]
      (loop [[row & r] rows acc headers]
        (if row
          (recur r (mapv (fn [a r] (conj a r)) acc row))
          acc)))))


(defn load-csv [filename]
  (with-open [reader (io/reader filename)]
    (let [[f & rows] (csv/read-csv reader)
          headers (map (fn [h] (keyword (str/replace h #"\s+" ""))) f)]
      (mapv (partial zipmap headers) rows))))

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
                (interleave h (map #(vec (take 2 %)) r))])))
         (str/join "\n")
         println)))

(def db {:dbtype "sqlite" :dbname "identifier.sqlite"})

(def ds (jdbc/get-datasource db))

(jdbc/execute! ds ["DROP TABLE IF EXISTS Master"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS TeamsFranchises"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Leagues"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Divisions"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Teams"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Managers"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Salaries"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS AwardsManagers"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS AwardsPlayers"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Fielding"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Pitching"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS PitchingPost"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS Batting"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS BattingPost"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS TeamsHalf"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS ManagersHalf"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS AwardsSharePlayers"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS AwardsShareManagers"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS AllstarFull"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS HallOfFame"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS SeriesPost"])
(jdbc/execute! ds ["DROP TABLE IF EXISTS FieldingOF"])


(jdbc/execute! ds [(slurp "migrations/003_Create_Master.sql")])
(jdbc/execute! ds [(slurp "migrations/004_Create_TeamsFranchises.sql")])
(jdbc/execute! ds [(slurp "migrations/005_Create_Leagues.sql")])
(jdbc/execute! ds [(slurp "migrations/006_Insert_Leagues_Data.sql")])
(jdbc/execute! ds [(slurp "migrations/007_Create_Divisions.sql")])
(jdbc/execute! ds [(slurp "migrations/008_Insert_Divisions_Data.sql")])
(jdbc/execute! ds [(slurp "migrations/009_Create_Teams.sql")])
(jdbc/execute! ds [(slurp "migrations/010_Create_Managers.sql")])
(jdbc/execute! ds [(slurp "migrations/011_Create_Salaries.sql")])
(jdbc/execute! ds [(slurp "migrations/012_Create_AwardsManagers.sql")])
(jdbc/execute! ds [(slurp "migrations/013_Create_AwardsPlayers.sql")])
(jdbc/execute! ds [(slurp "migrations/014_Create_Fielding.sql")])
(jdbc/execute! ds [(slurp "migrations/015_Create_Pitching.sql")])
(jdbc/execute! ds [(slurp "migrations/016_Create_PitchingPost.sql")])
(jdbc/execute! ds [(slurp "migrations/017_Create_Batting.sql")])
(jdbc/execute! ds [(slurp "migrations/018_Create_BattingPost.sql")])
(jdbc/execute! ds [(slurp "migrations/019_Create_TeamsHalf.sql")])
(jdbc/execute! ds [(slurp "migrations/020_Create_ManagersHalf.sql")])
(jdbc/execute! ds [(slurp "migrations/021_Create_AwardsSharePlayers.sql")])
(jdbc/execute! ds [(slurp "migrations/022_Create_AwardsShareManagers.sql")])
(jdbc/execute! ds [(slurp "migrations/023_Create_AllstarFull.sql")])
(jdbc/execute! ds [(slurp "migrations/024_Create_HallOfFame.sql")])
(jdbc/execute! ds [(slurp "migrations/025_Create_SeriesPost.sql")])
(jdbc/execute! ds [(slurp "migrations/026_Create_FieldingOF.sql")])

(defn load-dataset [migration-name]
  (let [path (io/file migration-name)
        file-name (.getName path)
        table-name (first (str/split file-name #"\."))]
    (doall
      (->> (load-csv migration-name)
           (map (fn [record]
                  (reduce
                    (fn [acc [k v]]
                      (if (re-matches #"^[0-9].*" (name k))
                        (assoc acc (format "\"%s\"" (name k)) v)
                        (assoc acc k v)))
                    {}
                    record)))
           (partition-all 5000)
           (map #(sql/insert-multi! ds table-name %))))
    (println (jdbc/execute! ds [(format "select COUNT(*) from %s" table-name)]))))

(load-dataset "local/baseball_databank/Master.csv")
(load-dataset "local/baseball_databank/TeamsFranchises.csv")
(load-dataset "local/baseball_databank/Teams.csv")
(load-dataset "local/baseball_databank/Managers.csv")
(load-dataset "local/baseball_databank/Salaries.csv")
(load-dataset "local/baseball_databank/AwardsManagers.csv")
(load-dataset "local/baseball_databank/AwardsPlayers.csv")
(load-dataset "local/baseball_databank/Fielding.csv")
(load-dataset "local/baseball_databank/Pitching.csv")
(load-dataset "local/baseball_databank/PitchingPost.csv")
(load-dataset "local/baseball_databank/Batting.csv")
(load-dataset "local/baseball_databank/BattingPost.csv")
(load-dataset "local/baseball_databank/TeamsHalf.csv")
(load-dataset "local/baseball_databank/ManagersHalf.csv")
(load-dataset "local/baseball_databank/AwardsSharePlayers.csv")
(load-dataset "local/baseball_databank/AwardsShareManagers.csv")
(load-dataset "local/baseball_databank/AllstarFull.csv")
(load-dataset "local/baseball_databank/HallOfFame.csv")
(load-dataset "local/baseball_databank/SeriesPost.csv")
(load-dataset "local/baseball_databank/FieldingOF.csv")

