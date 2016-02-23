(defproject dbutil3 "0.2.0"
  :description "A Clojure wrapper to DBUtils3"
  :url "https://github.com/tamurashingo/dbutils3-clojure"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/tamurashingo/dbutils3-clojure"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.github.tamurashingo.dbutil3/dbutil3 "0.2.0"]]
  :profiles {:dev {:dependencies [[com.h2database/h2 "1.4.181"]]}})

