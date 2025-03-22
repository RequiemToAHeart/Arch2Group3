Cache Simulation Project - Group 3

Project Overview

This project is a cache simulation system designed to analyze various test set scenarios based on the assigned cache mapping function and replacement policy. Our implementation follows the specifications given in the project guidelines.

Group Information
      • Group Number: 3
    • Cache Type: 4-way Set Associative (BSA) + Least Recently Used (LRU)
Cache Specifications
    • Cache Mapping: 4-way Set Associative
    • Replacement Policy: Least Recently Used (LRU)
    • Cache Line Size: 16 words
    • Number of Cache Blocks: 32 blocks
    • Read Policy: Non-load-through
    • Number of Memory Blocks: User-defined (minimum 1024 blocks)

Test Cases
The system is tested using three different test sequences:
    1. Sequential Sequence: Up to 2n cache blocks, repeated four times.
    2. Random Sequence: Contains 4n main memory blocks.
    3. Mid-Repeat Blocks Sequence: 
        ◦ Starts from block 0.
        ◦ Repeats the sequence in the middle two times up to n-1 blocks.
        ◦ Continues up to 2n.
        ◦ Repeats the full sequence four times.
Output
The simulation produces the following outputs:
    1. Cache Memory Snapshot 
        ◦ Memory blocks used in simulations: 1024, 4567, 6484, 7894.
        ◦ Each test case was executed four times.
        ◦ Option for step-by-step animated tracing or final memory snapshot.
        ◦ Text log of the cache memory trace.
    2. Performance Metrics 
        ◦ Memory Access Count
        ◦ Cache Hit Count
        ◦ Cache Miss Count
        ◦ Cache Hit Rate
        ◦ Cache Miss Rate
        ◦ Average Memory Access Time
        ◦ Total Memory Access Time

Analysis Report
Comparison and Analysis of Test Cases

![image](https://github.com/user-attachments/assets/7e0a89df-bf32-453b-b6b0-63fbba43c159)


Sequential Sequence:
    • Characteristics: 
        ◦ Memory accesses strictly follow a sequential order.
        ◦ Strong spatial locality allows for frequent cache hits.
    • Performance Impact: 
        ◦ Cache hit rate is highest, minimizing memory access latency.
        ◦ Very few cache misses reduce the need for expensive memory fetches.
        ◦ Demonstrates ideal conditions for a cache system, optimizing performance.
Random Sequence:
    • Characteristics: 
        ◦ Unpredictable memory accesses with no clear access pattern.
        ◦ Poor spatial locality causes frequent cache misses.
    • Performance Impact: 
        ◦ Cache hit rate is lowest, leading to constant memory access delays.
        ◦ High number of misses results in increased total memory access time.
        ◦ Illustrates worst-case scenario behavior for caching, highlighting inefficiencies.
Mid-Repeat Blocks Sequence:
    • Characteristics: 
        ◦ Combination of sequential access and repeated block accesses.
        ◦ Temporary locality benefits in the middle section, followed by sequential behavior.
    • Performance Impact: 
        ◦ Cache hit rate improves in the repeated section, reducing access time.
        ◦ Performance fluctuates but remains better than random access.
        ◦ Closely mimics real-world applications where some data is frequently reused.
Key Takeaways:
    • Sequential access maximizes efficiency, ensuring high hit rates and reduced memory stalls.
    • Random access is inefficient, leading to increased misses and slow memory access.
    • Mid-repeat access provides a middle ground, benefiting from repeated access patterns.
    • Optimizing memory layout and access patterns is crucial to achieving higher performance in cache-dependent applications.



Demonstration Video
    • https://drive.google.com/file/d/16RtQwpeq4Uj4QRvjKm0yUallvVKiVndi/view?usp=sharing
Repository Link
    • https://github.com/RequiemToAHeart/Arch2Group3.git
Contributors
    • Timothy Robert P. Bacud
    • Raidon Salvador R. Manaois
    • Angela Dominique C. Miguel
    • Francine Meryl Antoinette F. Perez
    • Ethan Paolo C. Pimentel
