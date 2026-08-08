#include <jni.h>
#include <string>

#include "wrapper/SolverBridge.hpp"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_debajit_cubesolver_solver_NativeSolver_solveCube(
        JNIEnv* env,
        jobject,
        jstring cubeState
) {

    const char* chars =
            env->GetStringUTFChars(cubeState, nullptr);

    std::string cube(chars);

    env->ReleaseStringUTFChars(
            cubeState,
            chars
    );

    std::string solution =
            SolverBridge::solve(cube);

    return env->NewStringUTF(
            solution.c_str()
    );
}