(ns BankOcr.core
  (:use [clojure.string :only [split]]))

(defn lines [input]
  (rest ; account for the leading \n
    (split input #"\n")))

(defn cols [line]
  (let [
    list-of-chars-lists (partition 3 line)]
    (map
      (partial apply str)
        list-of-chars-lists)))

(defn parse-it [input]
  (map cols (lines input)))

(defn get-col [lines,n]
  (apply str
    (map
      (fn [line] (nth line n))
        lines)))

(def value-map {
  " _ | ||_|" 0
  "     |  |" 1
  " _  _||_ " 2
  " _  _| _|" 3
  "   |_|  |" 4
  " _ |_  _|" 5
  " _ |_ |_|" 6
  " _   |  |" 7
  " _ |_||_|" 8
  " _ |_| _|" 9
  })

(defn value-of [digit]
  (value-map digit))

(defn read-digits [digits]
  (map value-of
    (map
      (partial get-col (parse-it digits))
        (range 9))))


