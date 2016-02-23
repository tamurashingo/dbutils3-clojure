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

(defn create-dbconnectionutil
  "create DBConnectionUtil wrapper."
  [conn]
  [(com.github.tamurashingo.dbutils3.DBConnectionUtil. conn)
   (ref :normal)])


(defn db-prepare
  "precompile the sql. If you want to use keyword parameter,
   call with optional key :type :param."
  [conn sql & {:keys [type]}]
  (cond
    (or (nil? type)
        (= type :normal))
    (do
      (. (conn 0) (prepare sql))
      (dosync (ref-set (conn 1) :normal)))

    (= type :param)
    (do
      (. (conn 0) (prepareWithParam sql))
      (dosync (ref-set (conn 1) type)))

    :else
    (throw
     (IllegalArgumentException. (clojure.string/join ["undefined type:" (name type)]))))
  conn)


(defn- create-param
  "create Param instance and put key-values."
  [param]
  (let [p (com.github.tamurashingo.dbutils3.Param.)]
    (dorun
     (for [key (keys param)]
       (. p put key (param key))))
    p))


(defn db-query
  ""
  [conn param]
  (cond
    (= @(conn 1) :param)
    (. (conn 0) (executeQueryWithParam (create-param param)))

    :else
    (. (conn 0) (executeQuery (object-array param)))))


(defn db-update
  ""
  [conn param]
  (cond
    (= @(conn 1) :param)
    (. (conn 0) (executeUpdateWithParam (create-param param)))

    :else
    (. (conn 0) (executeUpdate (object-array param)))))


(defn db-commit [conn]
  (. (conn 0) commit)
  conn)

(defn db-rollback [conn]
  (. (conn 0) rollback)
  conn)


