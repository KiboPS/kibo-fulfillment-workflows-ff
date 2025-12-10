# Quick Start Guide: Setting Up jBPM for Kibo Fulfillment Workflows

This Quick Start Guide will help you set up jBPM Business Central on your local system, preparing it to work seamlessly with the Kibo Fulfillment Workflows project. Whether you're new to jBPM or configuring your environment for a specific task, this guide will walk you through each step to get everything running smoothly.

## Prerequisites

Before you begin, ensure you have the following:

- A Mac computer
- Basic understanding of terminal commands
- Git installed on your system
- Internet access to download necessary files

## Step 1: Download and Install jBPM

1. Visit the [jBPM 7 downloads website](https://downloads.jboss.org/jbpm/release/7.74.1.Final/).
2. Download the latest stable version of jBPM ([7.74.1.Final](https://downloads.jboss.org/jbpm/release/7.74.1.Final/jbpm-server-7.74.1.Final-dist.zip)) for Mac.
3. Once the download is complete, unzip the downloaded file to a convenient location on your system.
4. [jBPM 7 documentation](https://docs.jbpm.org/7.74.1.Final/jbpm-docs/html_single/)

## Step 2: Start jBPM Business Central

1. Open your terminal.
2. Navigate to the unzipped jBPM folder.
3. Run the following command to start the jBPM server:

    ```bash
    jbpm-server/bin/standalone.sh
    ```

   - **If you encounter an OutOfMemory (OOM) error during startup:**

     a. Set the following environment variable to increase and optimize memory usage:

     ```bash
     export JAVA_OPTS="-XX:+UseG1GC -XX:+UseStringDeduplication -XX:MaxGCPauseMillis=250 -XX:G1ReservePercent=20 -server -d64 -Dfile.encoding=UTF-8 -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Dorg.apache.tomcat.websocket.DEFAULT_BUFFER_SIZE=10485760 -Xmx4g"
     ```

     b. After setting the environment variable, rerun the startup command:

     ```bash
     jbpm-server/bin/standalone.sh
     ```

     c. **If the OOM issue persists** after setting the environment variable, consider importing the Kibo Fulfillment Workflows project using a zip file instead of cloning the repository.
4. Wait for the server to fully start. You’ll know it’s ready when you see a message indicating that the server is listening on port 8080.
5. Open your web browser and go to:

    ```
    http://localhost:8080/business-central
    ```

## Step 3: Log In to Business Central

1. In your browser, log in using the default credentials:
   - **Username:** `wbadmin`
   - **Password:** `wbadmin`
2. After logging in, you’ll have access to the jBPM Business Central dashboard, where you can start managing your projects.

## Step 4: Obtain the Kibo Fulfillment Workflows Project

You can obtain the Kibo Fulfillment Workflows project either by cloning the repository or by downloading the repository as a zip file.

### Option A: Cloning the Repository

1. Open a new terminal window.
2. Navigate to the directory where you want to store the project.
3. Run the following git command to clone the Kibo Fulfillment Workflows project:

    ```bash
    git clone https://github.com/KiboSoftware/kibo-fulfillment-workflows.git
    ```

4. The project will be downloaded to your local machine, ready for use within jBPM Business Central.

### Option B: Downloading the Repository Zip (Recommended if OOM Issue Occurs)

1. If jBPM runs out of memory during startup after cloning and importing the repository, download a zip file of the Kibo Fulfillment Workflows project from the GitHub page instead.
2. Unzip the downloaded file into a directory of your choice.
3. Open a terminal and navigate to the unzipped directory.
4. Initialize a new Git repository by running:

    ```bash
    git init
    ```

5. Add all files and directories to the repository:

    ```bash
    git status
    ```

   - This command will show all untracked files and directories.
6. Add all files and directories to the Git index:

    ```bash
    git add -A
    ```

7. Commit the files to the repository:

    ```bash
    git commit -m "initial commit"
    ```

## Step 5: Create a Master Branch

Whether you cloned the repository (Option A) or downloaded and unzipped it (Option B), follow these steps to create a `master` branch:

1. Create a new branch named `master`:

    ```bash
    git checkout -b master
    ```

   You should see a confirmation that you've switched to the new `master` branch:

    ```
    Switched to a new branch 'master'
    ```

2. To verify the branch creation, run:

    ```bash
    git branch
    ```

   This will display the list of branches, with `master` being the active one:

    ```
      develop
    * master
    ```

   **NOTE:** The name `master` is used to align with the default branch name used by the development jBPM instance.

## Step 6: Import the Kibo Fulfillment Workflows Project into Business Central

1. In the jBPM Business Central dashboard, navigate to **Menu > Design > Projects**.
2. Click on the **Add Project** button located on the right side of the screen.
3. Select **Import Project** from the dropdown menu.
4. In the **Repository URL** field, enter the filesystem location where you cloned or unzipped the repository, using the following format:

    ```
    file:///{filesystem_location_of_cloned_or_unzipped_repository}
    ```

   - For example:

    ```
    file:///Users/your_username/projects/kibo-fulfillment-workflows
    ```

5. Click **Import** to load the project into Business Central.
6. Select the project that appears to confirm the import.
7. Click **Ok** to complete the process.

   **NOTE:** If you encounter an out-of-memory issue during startup after cloning and importing the repository, consider redoing the import using the zip download method outlined in Step 4, Option B.

## Step 7: Explore the Kibo Fulfillment Workflows Project

With the project successfully imported, you can now explore the Kibo Fulfillment Workflows project. This includes reviewing, modifying, and managing project assets such as processes, forms, rules, decision tables, and more directly within jBPM Business Central.

## Troubleshooting

If you encounter any issues during setup, consider the following tips:

- Ensure your Mac meets the system requirements for running jBPM.
- Verify that you’ve correctly entered the filesystem path when importing the project.
- If problems persist, consult the jBPM [documentation](https://docs.jbpm.org/7.74.1.Final/jbpm-docs/html_single/) or reach out for support.

---

You’re now set up and ready to explore and work with the Kibo Fulfillment Workflows project in jBPM Business Central. Dive in, start automating, and make the most of this powerful business automation toolkit!