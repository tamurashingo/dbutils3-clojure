DBUtils3 wrapper
================

A Clojure library designed to use DBUtil3.

## Usage ##

### create DBConnectionUtil ###

```clojure
(def dbconn (create-dbconnectionutil connection))
```


### precompile SQL ###

```clojure
(db-prepare dbconn "select  * from table where id = ?")
```

```clojure
(db-prepare dbconn "insert into table values (?, ?, ?)")
```


### execute query ###

```clojure
(db-query dbconn [3])
```


### execute update ###

```clojure
(db-update dbconn [3 4 "data"])
```


### transaction managment ###

```clojure
(db-commit dbconn)
(db-rollback dbconn)
```


## example ##

```clojure
(let [dbconn (create-dbconnectionutil conn)]
  (-> dbconn
      (prepare "select * from test where id = ?")
      (query [1])))
; --> [{"ID" "0", "NAME" "test00"}]

(let [dbconn (create-dbconnectionutil conn)]
  (-> dbconn
      (prepare "insert into test values (?, ?)")
      (update [1, "test01"])))
; --> 1

(let [dbconn (create-dbconnectionutil conn)]
  (-> dbconnn
      (prepare "insert into test values (?, ?)")
      ((fn [conn]
         (for [i (range 2 10)]
           (db-update conn [i (format "test%02d" i)]))))))
; --> (1 1 1 1 1 1 1 1 1 1 1)
```


License
-------
Copyright &copy; 2016 tamura shingo
Licensed under the [MIT License][MIT].

[MIT]: https://opensource.org/licenses/MIT
