# MontiSecArc IntelliJ Language Plugin
![Bildschirmfoto_2016-11-11_um_09.27.00](/uploads/bb4676c2ca848e1420f3d5ad710ce210/Bildschirmfoto_2016-11-11_um_09.27.00.png)
This projects brings the MontiSecArc language to the popular IntelliJ IDE. The plugin provides a deep integration of secure architecture files into the IDE. Allowing the fast creation of secure architectures and an plain learning curve.

**Downloads**

[IntelliJ_MSA_Language-0.8.9.SNAPSHOT.zip](http://138.68.65.103:8081/artifactory/intellij_plugins_snapshot_local/de/monticore/lang/montisecarc/plugin/IntelliJ_MSA_Language/0.8.9.SNAPSHOT/IntelliJ_MSA_Language-0.8.9.SNAPSHOT.zip) &#8226; [IntelliJ_MSA_Language-0.8.9.SNAPSHOT.jar](http://138.68.65.103:8081/artifactory/intellij_plugins_snapshot_local/de/monticore/lang/montisecarc/IntelliJ_MSA_Language/0.8.9.SNAPSHOT/IntelliJ_MSA_Language-0.8.9.SNAPSHOT.jar)

# Contents
- [Quickstart](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#quickstart)
- [Install Plugin into IntelliJ Installation](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#install_plugin_into_intelliJ_installation)
- [Usage Examples](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#usage_examples)
- [Features](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#features)
- [Create New Version](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#create_new_version)
- [Troubleshooting](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#troubleshooting)
- [Contribution](https://git.rwth-aachen.de/ma_buning/msa/edit/master/README.md#contribution)

# Quickstart
1. Check-Out project:

    `git clone https://git.rwth-aachen.de/ma_buning/msa.git --remote --recursive`
2. Import project into IntelliJ. Instructions can be found [here](https://www.jetbrains.com/help/idea/2016.3/importing-project-from-gradle-model.html).
3. Run an IDEA instance with the MSA language plugins pre-installed:
    1. Run/Debug `runIdea` from the gradle task list:
    ![Bildschirmfoto_2017-01-10_um_18.28.47](/uploads/d8076e406cd4dc2d90071552eacbca69/Bildschirmfoto_2017-01-10_um_18.28.47.png)

# Install Plugin into IntelliJ Installation
The plugin requires IntelliJ Version [2016.X.X](https://www.jetbrains.com/idea/download/) to be installed.

Download the newest version of the plugin from [here](http://138.68.65.103:8081/artifactory/intellij_plugins_snapshot_local/de/monticore/lang/montisecarc/plugin/IntelliJ_MSA_Language/0.8.9.SNAPSHOT/IntelliJ_MSA_Language-0.8.9.SNAPSHOT.zip). Do not unzip the file, just open the IntelliJ preferences and locate "Plugins" from the left menu.
![Bildschirmfoto_2016-11-11_um_09.38.51](/uploads/5870fcef0107641983a5800cd0fdc99f/Bildschirmfoto_2016-11-11_um_09.38.51.png)
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
- Graph Generation (with these two plugins installed: [1](https://git.rwth-aachen.de/ma_buning/graphdatabase.git), [2](https://plugins.jetbrains.com/idea/plugin/8087-graph-database-support))

# Create New Version
1. Update the version property in **gradle.properties** to the new version 
2. Execute the following gradle tasks:
    1. build > build
    2. build > jar
    3. intellij > buildPlugin
    4. publishing > artifactoryPublish

*x > y* describes the task folder in the IntelliJ Gradle view where x is the folder and y the task.

Publishing a new version requires a valid Artifactory user. This user needs to be created before and the credentials need to be saved as *artifactory_user* and *artifactory_password*. These two properties mustn't be checked into version control. Therefore, placing them in the global gradle.properties file is advised.


# Troubleshooting

**Gradle Building finds Bytecode Errors**

It is necessary to update the Java Version. Working (tested) is version 1.8.0_102

**Programming Language**

The program is mostly written in [Kotlin](kotlinlang.org), a short primer into the usage can be found [here](https://kotlinlang.org/docs/reference/)

# Contribution
We welcome contributions in the form of issues or pull requests. If you need help with this process, we've created a brief overview [here](https://git.rwth-aachen.de/ma_buning/msa/blob/master/CONTRIBUTING.md).