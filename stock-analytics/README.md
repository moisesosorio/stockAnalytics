# kdit Project

Skynet dependency and Skynet Maven plugin allow to perform the following functionalities:

* Initializes your ingestion files and schema files.
* Executes Kirby job (or another Spark job) in your local machine.
* Migrates DD templates.


This project contains a file named ``skynet.conf`` used to set some configuration variables. Be aware to fill this file
before starting using Skynet.

If you want to use Skynet functionalities, you can use **skynet-cli** module through IntelliJ configuration or
**skynet-maven-plugin** through command line.


1. Add new Run/Debug Configuration of type Application (in the Run menu).
2. Set ``com.datio.skynet.cli.SkynetCli`` as the main class.
3. Set ``${project-path}/kdit-ingestion/kdit`` as working directory.
4. Set ``kdit`` in "Use classpath of module" option.
5. Fill "Program arguments" field with the action and arguments you want (arguments for each step are
detailed below):

    1. Initialize your ingestion files: ``init``.
    2. Initialize schema files: ``init-schemas``.
    3. Deploy your ingestion files in Git and create a PR: ``deploy``.
    4. Deploy your schema files in Git and create a PR: ``deploy-schemas``.
    5. Change to another Git branch: ``go-to``
    6. Run local Kirby jobs: ``run-kirby``.
    7. Run local Spark jobs: ``run-job``.
    8. Migrate DD templates to the latest Governance official template: ``migrate-template``.

NOTE: when creating the project via the archetype, you will have in the root folder a folder named ``runConfigurations``.
If you copy this folder into the ``.idea`` folder (after creating the project in IntelliJ) and reopen the project,
you'll get all configurations already done.


