(ns goldenstand.test
  "/api/test endpoints."
  (:require [cheshire.core :as json]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [compojure.core :refer [DELETE GET POST PUT]]
            [metabase
             [query-processor :as qp]
             [util :as u]]
            [metabase.api.common :as api]
            [metabase.mbql.schema :as mbql.s]
            [metabase.models
             [card :refer [Card]]
             [database :as database :refer [Database]]
             [query :as query]]
            [metabase.query-processor
             [streaming :as qp.streaming]
             [util :as qputil]]
            [metabase.query-processor.middleware
             [constraints :as qp.constraints]
             [permissions :as qp.perms]]
            [metabase.util
             [i18n :refer [trs tru]]
             [schema :as su]]
            [schema.core :as s]
            [toucan
             [db :as db]
             [hydrate :refer [hydrate]]]
            [goldenstand.Models :refer [Models]]
            [goldenstand.Properties :refer [Properties]]
            [goldenstand.Objects :refer [Objects]]
            )
  )
(defn- query->source-card-id
  "Return the ID of the Card used as the \"source\" query of this query, if applicable; otherwise return `nil`. Used so
  `:card-id` context can be passed along with the query so Collections perms checking is done if appropriate. This fn
  is a wrapper for the function of the same name in the QP util namespace; it adds additional permissions checking as
  well."
  [outer-query]
  (when-let [source-card-id (qputil/query->source-card-id outer-query)]
    (log/info (trs "Source query for this query is Card {0}" source-card-id))
    (api/read-check Card source-card-id)
    source-card-id))

(api/defendpoint ^:streaming POST "/"
                 "Execute a query and retrieve the results in the usual format."
                 []
   "Execute a query and retrieve the results in the usual format."
   (db/select Models))

(api/defendpoint ^:streaming POST "/id"
   "Get Dashboard with ID."
                 [:as {{id :id} :body}]
                 ;{id         (s/maybe s/Int)}
                 (println id)
                 (db/select Objects :model_id id))


(api/define-routes)
