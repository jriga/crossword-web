(defproject serve "0.1.0"
  :description "Serve vue crossword"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
;;                 [http-kit "2.3.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/data.json "0.2.6"]
                 [crossword "0.1.0"]
                 [environ "1.1.0"]]
;;  :main ^:skip-aot serve.core
;;  :target-path "target/%s"
;;  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "serve-standalone.jar"
  :profiles {:production {:env {:production true}}})
