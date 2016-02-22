(ns dbutils3.core)

;;
;; (let [dbconn (create-dbconnectionutil conn)]
;;   (-> dbconn
;;       (prepare "select * from test where id = ?")
;;       (query [1])))
;; (let [dbconn (create-dbconnectionutil conn)]
;;   (-> dbconn
;;       (prepare "insert into test values (?, ?)")
;;       (update [1, "test01"])))
;;
;; (let [dbconn (create-dbconnectionutil conn)]
;;   (-> dbconnn
;;       (prepare "insert into test values (?, ?)")
;;       ((fn [conn]
;;          (for [i (range 2 10)]
;;            (db-update conn [i (format "test%02d" i)]))))))

(defn create-dbconnectionutil [conn]
  "create DBConnectionUtil"
  (com.github.tamurashingo.dbutils3.DBConnectionUtil. conn))

(defn db-prepare [conn sql]
  (. conn (prepare sql))
  conn)

(defn db-query [conn param]
  (. conn (executeQuery (object-array param))))

(defn db-update [conn param]
  (. conn (executeUpdate (object-array param))))

(defn db-commit [conn]
  (. conn commit)
  conn)

(defn db-rollback [conn]
  (. conn rollback)
  conn)


