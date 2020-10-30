(ns serve.core
  (:require ;;[org.httpkit.server :as server]
            [ring.adapter.jetty :as jetty]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [crossword.core :as cw]))


(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

(defn request-example [req]
     {:status  200
      :headers {"Content-Type" "text/html"}
      :body    (let [max (Integer/parseInt (get-in req [:params :max] "7"))
                     min (Integer/parseInt (get-in req [:params :min] "3"))
                     limit (Integer/parseInt (get-in req [:params :limit] "10"))]
                 (-> (take limit
                           (cw/words "words.txt" 
                                     {:max-length max
                                      :min-length min
                                      :exclude-pattern #"\s|\d|\W"}))
                     (json/write-str)
                     str))})

(defroutes app-routes
  (route/resources "/")
  (GET "/request" [] request-example)
  (route/not-found "Error, page not found!"))

(defn -main
  [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (wrap-defaults #'app-routes api-defaults) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))


