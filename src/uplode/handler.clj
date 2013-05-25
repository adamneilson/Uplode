(ns uplode.handler
  (:use [compojure.core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [taoensso.timbre :as timbre :refer (trace debug info warn error report)]
            (ring.middleware [multipart-params :as mp])
            [aws.sdk.s3 :as s3]))


; AWS Access credentials
(def cred {
           :access-key "++++++++YOUR_AWS_S3_ACCESS_KEY++++++", 
           :secret-key "++++++YOUR_AWS_S3_SECRET_KEY+++++"})

(def BUCKET "Uplode")

; for now 1MB but this should be configurable
(def MAX_UPLOAD_SIZE 1048576)


(defn store-file "stores a file on S3" [file-name file]
    (s3/put-object cred BUCKET file-name file)
    (s3/update-object-acl cred BUCKET file-name (s3/grant :all-users :read)))



(defn upload-file
  [params]
  (let [file (get params :file)]
    (debug file)

    ; check the file is an image
    (if (not (.startsWith (file :content-type) "image/"))
      (throw (Exception. "Uploaded file was not an image!")))

    ; check the file isn't over a max size
    (if (> (file :size) MAX_UPLOAD_SIZE) 
      (throw (Exception. "Uploaded file was too big!")))

    ; if all is good, store the file on S3
    (store-file (file :filename) (clojure.java.io/file (file :tempfile)))

    ; send back an all's good message
    "OK"))

(defroutes app-routes
  (route/resources "/")
  (GET "/" {params :params} (slurp "resources/public/index.html"))
  (mp/wrap-multipart-params (POST "/file" {params :params} (upload-file params)))
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
