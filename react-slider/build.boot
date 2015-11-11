(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.10" :scope "test"]
                  [cljsjs/boot-cljsjs "0.5.0"  :scope "test"]
                  [cljsjs/react       "0.13.3-1"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "0.4.2-0")
(bootlaces! +version+)

(task-options!
 pom  {:project     'cljsjs/react-slider
       :version     +version+
       :description "CSS agnostic slider component for React."
       :url         "https://github.com/mpowaga/react-slider"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask download-react-slider []
  (download :url      "https://github.com/mpowaga/react-slider/archive/v0.4.2.zip"
            :checksum "afbc5e5038c8f7a9ee64118fbc314b5b"
            :unzip    true))

(deftask package []
  (comp
    (download-react-slider)
    (sift :move {#"^react-.*/react-slider.js"
                 "cljsjs/react-slider/development/react-slider.inc.js"})
    (minify :in "cljsjs/react-slider/development/react-slider.inc.js"
            :out "cljsjs/react-slider/production/react-slider.min.inc.js")
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react-slider"
               :requires ["cljsjs.react"])))
