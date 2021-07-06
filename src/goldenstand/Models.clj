(ns goldenstand.Models
  (:require [clojure.set :as set]
            [clojure.tools.logging :as log]
            [metabase
             [public-settings :as public-settings]
             [util :as u]]
            [metabase.api.common :as api :refer [*current-user-id*]]
            [metabase.mbql
             [normalize :as normalize]
             [util :as mbql.u]]
            [metabase.middleware.session :as session]
            [metabase.models
             [collection :as collection]
             [dependency :as dependency]
             [field-values :as field-values]
             [interface :as i]
             [params :as params]
             [permissions :as perms]
             [query :as query]
             [revision :as revision]]
            [metabase.models.query.permissions :as query-perms]
            [metabase.plugins.classloader :as classloader]
            [metabase.query-processor.util :as qputil]
            [metabase.util.i18n :as ui18n :refer [tru]]
            [toucan
             [db :as db]
             [models :as models]]))
(models/defmodel Models :models)

;(u/strict-extend (class TestModel)
;                 models/IModel
;                 (merge models/IModelDefaults
;                        {:hydration-keys (constantly [:database :db])
;                         :types          (constantly {:id                     :int})
;                         :properties     (constantly {:timestamped? false})
;                         }))



