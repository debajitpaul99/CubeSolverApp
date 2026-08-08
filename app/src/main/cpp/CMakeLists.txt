cmake_minimum_required(VERSION 3.22.1)

project(CubeSolverNative)

set(ROB_TWOPHASE_DIR
        ${CMAKE_CURRENT_SOURCE_DIR}/../../../../third_party/rob-twophase/src
)

add_library(
        cubesolver
        SHARED

        NativeSolver.cpp
        wrapper/SolverBridge.cpp

        ${ROB_TWOPHASE_DIR}/face.cpp
        ${ROB_TWOPHASE_DIR}/cubie.cpp
        ${ROB_TWOPHASE_DIR}/move.cpp
        ${ROB_TWOPHASE_DIR}/coord.cpp
        ${ROB_TWOPHASE_DIR}/sym.cpp
        ${ROB_TWOPHASE_DIR}/prun.cpp
        ${ROB_TWOPHASE_DIR}/solve.cpp
)

target_include_directories(
        cubesolver
        PRIVATE
        ${ROB_TWOPHASE_DIR}
)

find_library(
        log-lib
        log
)

target_link_libraries(
        cubesolver
        ${log-lib}
)