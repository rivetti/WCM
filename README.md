# WCM - Splitter and Proportional Windowed Count-Min

This repository contains the implementation of the Splitter and Windowed Count-Min algorithms, ie, two extension of the Count-Min Sketch algorithms presented by Cormode and Muthukirshnan to the Sliding Window Model

We present the details of these algorithms in the paper Nicolo' Rivetti, Yann Busnel and Achour Mostefaoui, Efficient Summarizing  Data Streams over Sliding Windows, Proceedings of the 14th IEEE International Symposium on Network Computing and Application, NCA, 2015.

Abstract:
Estimating the frequency of any piece of information in large-scale distributed data streams became of utmost importance in the last decade (e.g., in the context of network monitoring, big data, etc.). If some elegant solutions have been proposed recently, their approximation is computed from the inception of the stream. In a runtime distributed context, one would prefer to gather information only about the recent past. This may be led by the need to save resources or by the fact that recent information is more relevant.

In this paper, we consider the sliding window model and propose two different (on-line) algorithms that approximate the items frequency in the active window. More precisely, we determine a (𝜀,𝛿)-additive-approximation meaning that the error is greater than 𝜀 only with probability 𝛿. These solutions use a very small amount of memory with respect to the size N of the window and the number n of distinct items of the stream, namely, O(1/𝜀 log(1/𝛿)(log N + log n)) and O(1/𝜏𝜀 log(1/𝛿)(log N + log n)) bits of space, where 𝜏 is a parameter limiting memory usage. We also provide their distributed variant, i.e., considering the sliding window functional monitoring model. We compared the proposed algorithms to each other and also to the state of the art through extensive experiments on synthetic traces and real data sets that validate the robustness and accuracy of our algorithms.
