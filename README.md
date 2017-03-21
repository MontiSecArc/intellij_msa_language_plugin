# MontiSecArc IntelliJ Language Plugin
![Bildschirmfoto_2016-11-11_um_09.27.00](/resources/img/Bildschirmfoto_2016-11-11_um_09.27.00.png)
[![Build Status](https://travis-ci.org/MontiSecArc/intellij_msa_language_plugin.svg?branch=master)](https://travis-ci.org/MontiSecArc/intellij_msa_language_plugin) [![codecov](https://codecov.io/gh/MontiSecArc/intellij_msa_language_plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/MontiSecArc/intellij_msa_language_plugin)


This projects brings the MontiSecArc language to the popular IntelliJ IDE. The plugin provides a deep integration of secure architecture files into the IDE. Allowing the fast creation of secure architectures and an plain learning curve.

# Quickstart
1. Check-Out project:

    `git clone https://git.rwth-aachen.de/ma_buning/msa.git --remote --recursive`
2. Import project into IntelliJ. Instructions can be found [here](https://www.jetbrains.com/help/idea/2016.3/importing-project-from-gradle-model.html).
3. Run an IDEA instance with the MSA language plugins pre-installed:
    1. Run/Debug `runIdea` from the gradle task list:
    ![Bildschirmfoto_2017-01-10_um_18.28.47](/resources/img/Bildschirmfoto_2017-01-10_um_18.28.47.png)

# Install Plugin into IntelliJ Installation
The plugin requires IntelliJ Version [2016.X.X](https://www.jetbrains.com/idea/download/) to be installed.

Download the newest version of the plugin from [here](https://github.com/MontiSecArc/intellij_msa_language_plugin/releases/latest). Do not unzip the file, just open the IntelliJ preferences and locate "Plugins" from the left menu.
![Bildschirmfoto_2016-11-11_um_09.38.51](/resources/img/Bildschirmfoto_2016-11-11_um_09.38.51.png)
Click the "Install plugin from disk" button and select the downloaded zip file.

# Usage Examples
## Simple Environment Component
```java
package de.monticore.lang.montisecarc.simple.secarc;

import de.monticore.lang.montisecarc.simple.secarc.ServiceA;
import de.monticore.lang.montisecarc.simple.secarc.ServiceB;

// Enclosing Component Environment
component ServiceEnvironment {

    // The environment is untrusted
    trustlevel -1 "Untrusted public network";
    
    // Create instance of service A
    ServiceA serviceA;
    
    // Create instance of service B
    ServiceB serviceB;
    
    // Service B is strongly authenticated at service A
    identity strong serviceB -> serviceA;
    
    // Connect the defined ports
    // Uses default connection protocol but requires it to be encrypted
    connect encrypted serviceB.outDataPackage -> serviceA.inDataPackage;
    
    // Connect two ports
    // Define the used protocol for the connection as HTTPS
    connect serviceB.outDataPackage -[HTTPS]-> serviceA.inDataPackage;
}
```
## Simple Service Component
```java
package de.monticore.lang.montisecarc.simple.secarc;

import de.monticore.lang.montisecarc.simple.msg.*;

component ServiceA {

    // Access to the service needs to be checked by identities
    accesscontrol on;
    
    // Define that inDataPackage port can only be access by privilegedUser
    access inDataPackage(privilegedUser);
    
    // Define ingoing port
    port in DataPackage inDataPackage;
}
```

# Features
- Syntax Highlighting
- Keyword-Autocompletion
- Brace Matching
- New File Integration
- MontiSecArc templates for components and ports
- Component and port folding support
- Contex Condition checks
- Reference Resolution
- Find-Usage
- Graph Generation (with these two plugins installed: [1](https://github.com/MontiSecArc/graphdatabase/releases/latest), [2](https://plugins.jetbrains.com/idea/plugin/8087-graph-database-support))

# Create New Version
New versions are automatically created by creating a new Git Tag.

# Troubleshooting

**Gradle Building finds Bytecode Errors**

It is necessary to update the Java Version. Working (tested) is version 1.8.0_102

**Programming Language**

The program is mostly written in [Kotlin](kotlinlang.org), a short primer into the usage can be found [here](https://kotlinlang.org/docs/reference/)

# Contribution
We welcome contributions in the form of issues or pull requests. If you need help with this process, we've created a brief overview [here](https://github.com/MontiSecArc/intellij_msa_language_plugin/blob/master/CONTRIBUTING.md).
