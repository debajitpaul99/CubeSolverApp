#include "SolverBridge.hpp"

#include "face.h"
#include "move.h"
#include "coord.h"
#include "sym.h"
#include "prun.h"

#include <string>
#include <chrono>
#include <android/log.h>

#define TAG "CubeSolverNative"

namespace {

    bool initialized = false;

}

void SolverBridge::initialize() {

    if (initialized)
        return;

    auto start = std::chrono::steady_clock::now();

    __android_log_print(
            ANDROID_LOG_INFO,
            TAG,
            "Initialization started"
    );

    face::init();

    move::init();

    coord::init();

    sym::init();

    __android_log_print(
            ANDROID_LOG_INFO,
            TAG,
            "Basic initialization completed"
    );

    /*
     * TEMPORARILY DISABLED
     *
     * prun::init(false) generates the huge pruning tables.
     * It freezes the UI because it runs on the main thread.
     *
     * We'll move this to a background thread later.
     */

    // prun::init(false);

    auto end = std::chrono::steady_clock::now();

    auto initTime =
            std::chrono::duration_cast<std::chrono::milliseconds>(
                    end - start
            ).count();

    __android_log_print(
            ANDROID_LOG_INFO,
            TAG,
            "Initialization finished in %lld ms",
            (long long)initTime
    );

    initialized = true;
}

std::string SolverBridge::solve(
        const std::string& cubeString
) {

    initialize();

    __android_log_print(
            ANDROID_LOG_INFO,
            TAG,
            "Received cube: %s",
            cubeString.c_str()
    );

    cubie::cube cube;

    int err = face::to_cubie(
            cubeString,
            cube
    );

    if (err != 0) {

        return "Face Error: " + std::to_string(err);

    }

    err = cubie::check(cube);

    if (err != 0) {

        return "Cube Error: " + std::to_string(err);

    }

    return "Cube Valid";

}