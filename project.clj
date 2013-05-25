(defproject uplode "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [com.floatbackwards/multipart "0.0.6"]
                 [com.taoensso/timbre "1.5.2"]
                 [clj-aws-s3 "0.3.5"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler uplode.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
