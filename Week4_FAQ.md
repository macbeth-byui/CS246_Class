**Git and Github Resources**

In CS246, the most common commands that you will use are:

* git clone - Copy a repository from Github to your computer.  If you have collaborator privileges, then you will be able to push changes.
* git add <file> - Stage changes that you have made (use "." to stage all modified files you have made).  A staged file can be committed.
* git commit -m <comment> - Commit changes to your git repository
* git pull - Get all changes made from the Github repository (also called the remote) and merge them into your repository.  This may cause a merge conflict that you will have to manually fix.
* git push - Put all committed changes into the Githbu repository.  You should pull before your push.

Here are some good resources:

* Reference Manual that explains how each git command works: https://git-scm.com/docs
* E-Book for using Git: https://git-scm.com/book/en/v2
* How-to for Un-doing things in Git: https://www.atlassian.com/git/tutorials/undoing-changes

**HEAD**

In Git, the HEAD is the branch you are currently working in. It also can be expressed by saying its the most recent commit in your current branch.  Here is a link to a Git glossary:  https://git-scm.com/docs/gitglossary

**GUI Git Tools**

The "git bash" is a command line that has environment variables setup to make it a little easier to run git.  You can run git from any command line or terminal.  IDE's like IntelliJ (and many others) provide support for git.  If you have git already installed, then the IDE's will run the git commands for you.  However, some problems may not be able to be solved with the IDE.  It is always good to have a basic understanding of how to use git from the command line.

There are several graphical tools that you can use instead of running git commands or relying on the git integration within the IDE.  Here is a link to several: https://git-scm.com/downloads/guis

If you are solving a problem or trying to do branching, having a graphical representation of your branches can be helpful.  The GUI tools above can do this for you.

**IntelliJ and Git**

The basic commands of add, commit, pull, and push are all available (including branching) in IntelliJ.  If you get a conflict on a merge, IntelliJ will provide you a tool to manually merge the changes.  

Here are 2 use cases that are common:
* New Project - Create the project new in IntelliJ (or Android Studio) and then select "Import into Version Control" from the VCS menu option.  Select "Share Project on Github".  You will be prompted for details about the repository you want to create in Github.
* Existing Project - Select "Checkout from Version Control", and then select "git", and then specify the Github repository that you want to open.

**Git Troubleshooting**

If you are running into git problems using IntelliJ, you may want to try to do the actions from the command line.  Rely on "git status" to see what is going on.  If you are on the command line, git will frequently give you hints about what needs to be done to solve the problem.

**What can be stored in Git**

Normally, we store code files in git.  However, you can store any file including requirements documents, design diagrams, user guides, and test cases.

**Security**

If you have a public repository, then everyone in the world will see your code.  However, they won't be able to change your code.  They can clone your repository and change it in their own repository.

If you want to keep your code secure, put it into a private repository.  You CS246 android app should be put into a private repository with your entire team and the teacher as collaborators.

**Branching & Merging**

Branching is useful in the following scenarios:
* Usually each software bug or new feature is assigned a change control ID so that the team can control changes made to the software.  In this type of environment, a separate branch would be created for each approved change.  The engineer would test and branch and do a rebase to integrate it with the master branch.  When the change was fully tested, the branch would be merged in.
* If you wanted to prototype an idea, you could create a branch to work on the idea seperate from the rest of the project.  Occasionally, you would rebase to keep your mini-project in synch with the master branch.  If desired, the prototype idea could be merged into the master.

In your CS246 project, it is unlikely that you will use branching.  You will most likely just need to add, commit, pull, and push.

When you work with a branch, these are common commands
* git branch -a - Show all branches
* git branch -d my_branch - Delete "my_branch".  Do this only after merging it to the master branch
* git branch my_branch - Create a branch called "my_branch"
* git checkout my_branch - Move to the "my_branch" branch.  Files in your hard drive will physically move around when you do this.  Use branch "master" to go back to the master branch.
* git rebase master - While you are in your branch, you can issue this command to re-synch your branch with the master.  This will merge in commits that were put into the master branch since you originally created your branch (or since you last did a rebase)
* git merge my_branch - While you are in the master branch, you can merge you final changes from the "my_branch" branch.  Only do this when are finished with your branch.

The GUI tools can be helpful in working with branches because its easier to move between branches and you can see the effects of merging graphically.

Here is a link to more advanced topics of branching:  https://git-scm.com/book/en/v2/Git-Tools-Advanced-Merging

Whenever you do a "git checkout" you are changing the HEAD.  Normally you specify a branch name (or master).  Its possible to checkout a commit which is called a detached HEAD.  This was done in the tutorial to show you how the HEAD can move around.  This is not something you will usually need to do.

**Github**

When you create a github repository, here are the commands that you can use to setup your git repository on your hard drive:

To clone the repository: 

    git clone <url from github>

To use an existing repository:

    git remote add origin <url from github>
    git push -u origin master

**Conflicts**

When you do any merge (either because of a pull, rebase, or merge), git will attempt to merge the changes automatically.  If the same lines of a file were modified by multiple people, a merge conflict will occur.  Git will give you a message that a conflict exists.  When you look in the code, you will see the following type of text:

    <<<<<<< HEAD
    System.out.println("Hello Universe!");
    =======
    System.out.println("Hello World!");
    >>>>>>> test

The first println was from the HEAD (We were trying to merge into this).  The other came from another branch (in this the "test" branch ... or it could be from GitHub).  You need to manually modify these lines of code (removing the <<< >>> ==== lines as well) to show the correct code you want.  This may require that you work together with you team to determine the correct code.

**.gitignore**

Creating a file called .gitignore in your repository can allow you to specify which files should not be stored in Git and Github.  Here is the .gitignore I use for Java:

    *.iml
    local.properties
    .gradle
    .idea/
    build/
    gradle/
    captures/
    .DS_Store
    out/




