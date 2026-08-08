#ifndef SOLVER_BRIDGE_HPP
#define SOLVER_BRIDGE_HPP

#include <string>

class SolverBridge {

public:

    static void initialize();

    static std::string solve(
            const std::string& cubeString
    );

};

#endif