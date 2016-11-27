(ns dataframe.series-test
  (:require [dataframe.series :as srs :refer [index]]
            [expectations :refer [expect]]))


(expect '(0 1 2)
        (let [my-srs (srs/series '(:x :y :z))]
          (index my-srs)))

(expect 1
        (let [my-srs (srs/series '(1 2 3) '("A" "B" "C"))]
          (srs/ix my-srs "A")))

(expect nil
        (let [my-srs (srs/series '(1 2 3)'("A" "B" "C"))]
          (srs/ix my-srs "D")))


(expect AssertionError
        (let [my-srs (srs/series '(1 2 3) '("A" "B" "A"))]
          (srs/ix my-srs "D")))


(expect {:a 1 :b 2 :c 3}
        (-> (srs/series '(1 2 3) '(:a :b :c))
            srs/srs->map))


; Test iteration
(expect '([:a 1] [:b 2] [:c 3])
        (map identity
             (srs/series [1 2 3] [:a :b :c])))


(expect (srs/series [2 4 6] [:a :b :c])
        (srs/mapvals
          (srs/series [1 2 3] [:a :b :c])
          #(* 2 %)))


(expect (srs/series [1 2 3] [:c :d :e])
        (srs/set-index
          (srs/series [1 2 3] [:a :b :c])
          [:c :d :e]))


(expect (srs/series [2 4] [:b :d])
        (srs/select
          (srs/series [1 2 3 4] [:a :b :c :d])
          [false true nil "true"]))