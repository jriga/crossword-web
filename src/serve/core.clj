(ns serve.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [crossword.core :as cw])
  (:gen-class))


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
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))

