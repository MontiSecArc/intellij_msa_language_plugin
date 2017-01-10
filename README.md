# MontiSecArc IntelliJ Language Plugin
![Bildschirmfoto_2016-11-11_um_09.27.00](/uploads/bb4676c2ca848e1420f3d5ad710ce210/Bildschirmfoto_2016-11-11_um_09.27.00.png)
This projects brings the MontiSecArc language to the popular IntelliJ IDE. The plugin provides a deep integration of secure architecture files into the IDE. Allowing the fast creation of secure architectures and an plain learning curve.

# Contents
- [Quickstart](quickstart)
- [Install Plugin into IntelliJ Installation](install_plugin_into_intelliJ_installation)
- [Usage Examples](usage_examples)
- [Features](features)
- [Create New Version](create_new_version)
- [Troubleshooting](troubleshooting)
- [Contribution](contribution)

# Quickstart
1. Check-Out project:

    `git clone https://git.rwth-aachen.de/ma_buning/msa.git --remote --recursive`
2. Import project into IntelliJ. Instructions can be found [here](https://www.jetbrains.com/help/idea/2016.3/importing-project-from-gradle-model.html).
3. Run an IDEA instance with the MSA language plugins pre-installed:
    1. Run/Debug `runIdea` from the gradle task list:
    ![Bildschirmfoto_2017-01-10_um_18.28.47](/uploads/d8076e406cd4dc2d90071552eacbca69/Bildschirmfoto_2017-01-10_um_18.28.47.png)

# Install Plugin into IntelliJ Installation
The plugin requires IntelliJ Version [2016.X.X](https://www.jetbrains.com/idea/download/) to be installed.

Download the newest version of the plugin from [here](https://git.rwth-aachen.de/ma_buning/msa/builds/3094/artifacts/file/build/distributions/IntelliJ_MSA-0.7.7.SNAPSHOT.zip). Do not unzip the file, just open the IntelliJ preferences and locate "Plugins" from the left menu.
![Bildschirmfoto_2016-11-11_um_09.38.51](/uploads/5870fcef0107641983a5800cd0fdc99f/Bildschirmfoto_2016-11-11_um_09.38.51.png)
Click the "Install plugin from disk" button and select the downloaded zip file.

# Usage Examples

# Features

# Create New Version

# Troubleshooting

**Gradle Building finds Bytecode Errors**

It is necessary to update the Java Version. Working (tested) is version 1.8.0_102

# Contribution
We welcome contributions in the form of issues or pull requests. If you need help with this process, we've created a brief overview [here](https://git.rwth-aachen.de/ma_buning/msa/blob/master/CONTRIBUTING.md).