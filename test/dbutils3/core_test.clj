(ns dbutils3.core-test
  (:require [clojure.test :refer :all]
            [dbutils3.core :refer :all]))

(deftest create-dbconnectionutil-test
  (testing "create-dbconnectionutil"
    (let [conn (java.sql.DriverManager/getConnection "jdbc:h2:mem:test")]
      (try
        (let [dbconn (create-dbconnectionutil conn)]
          (is (not (nil? dbconn)))
          (is (instance? com.github.tamurashingo.dbutils3.DBConnectionUtil dbconn)))
        (finally
          (. conn close))))))


(deftest createtable-update-query-test
  (testing "insert values"
    (let [conn (java.sql.DriverManager/getConnection "jdbc:h2:mem:test")
          dbconn (create-dbconnectionutil conn)]
      (try
        (-> dbconn
            (db-prepare "create table test (id int primary key, name varchar)")
            (db-update []))
        (is (= 1
               (-> dbconn
                   (db-prepare "insert into test values (?, ?)")
                   (db-update [1, "test01"]))))
        (is (= [{"ID" "1", "NAME" "test01"}]
               (-> dbconn
                   (db-prepare "select ID, NAME from test where id = ?")
                   (db-query [1]))))
        (finally
          (. conn close))))))

