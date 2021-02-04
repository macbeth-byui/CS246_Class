## CS 246 Week 4 Support Material

**Examples and Videos**

The following examples and videos support the material in Week 4.  Subsequent sections below provide additional information and examples for common questions that students encounter during this week.

* Video: https://video.byui.edu/media/0_vrhdablk
* Git Tutorial Worksheet: https://macbeth-byui.github.io/CS246/diagrams/git_class_tutorial.pdf
* Example of Configuration Management Objectives in Avionics: https://macbeth-byui.github.io/CS246/diagrams/DO-178C_ConfigMgmt.pdf

**Version Control**

Version control in general is the ability to track and manage changes to something.  In our case, the most common thing we are tracking is code.  You can also track requirements, designs, tests, and releases (amongst many other things).  There are other tools that may serve you better for requirements such as DOORS or Google Docs.  

There is always an debate during software development if we should apply version control during the early days (the prototype phase).  It does require extra work to use these tools and frequently individuals will forgo the use of tools during these early phases.  However, if you do use the tools, you get the benefit of remembering where you have been.  If you try something and it doesn't work but you don't remember what changes you made, then we are very thankful if we used version control tools.  In general, a software engineer will use these tools for all of their work (even if they are working alone).

Version Control is not just for software.  You can use these tools to manage all your files for various courses and projects.  Binary files can also be stored in these tools (e.g. Microsoft word files) but you won't be able to see differences between the files.  When you commit these files, they will replace the old version thus not performing any merge.

**Git and Github Resources**

Many companies use Git and GitHub (take a look at this site:   https://stackshare.io/github )  One of the benefits is that the git tools are free and the GitHub site (while not always free) provides an immediate version control environment that doesn't have to be installed and can be easily managed.  The command structures are complicated, but there are a few command (and tools) that if you learn you can be successful.  

Note that git will version control files only.  An empty folder will not go into version control.

The location that you create (either with init or clone) a repository is the location it will exist on your hard drive.  You can put it anywhere.  It can be wise to have a special place to put your repositories.  If IntelliJ creates the repository, then it will be placed wherever your project already exists.

In CS246, the most common commands that you will use are:

* git clone - Copy a repository from Github to your computer.  If you have collaborator privileges, then you will be able to push changes.
* git add `file` - Stage changes that you have made (use "." to stage all modified files you have made).  A staged file can be committed.  If you only have one (or a few) files that you want to commit, then only add those files (don't use the ".").  The process of adding just a few of the files might be difficult to keep track of.
* git commit -m `comment` - Commit changes to your git repository
* git pull - Get all changes made from the Github repository (also called the remote) and merge them into your repository.  This may cause a merge conflict that you will have to manually fix.
* git push - Put all committed changes into the Githbu repository.  You should pull before your push.  If you forget to do the pull, your push may fail if it conflicts with changes other people made.
* git log --oneline - Display all changes (commits) made in the repository
* git remote add origin `url` - Used to connect a git repository on your computer to a github repository (the url)

If you made a mistake (pushed something broken), there are commands to help you undo commits.  However, it is often easier and safer to first determine what is wrong with the code and make the code change with another commit.  The following commands are useful when you made a mistake and need to undo something:

* git revert HEAD - Undo the most recent commit.  You will need to follow this with a push to update GitHub
* git reset --hard - Undo all local changes you've made but have not committed yet
* git reset --hard `commit_id`- Moves the HEAD back to this commit.  You will remove any uncommitted changes.  You can get the commit id by using the "git log --oneline" command above.  This will not remove any commits.  To push to GitHub in this scenario, you need to do "git push origin -f"
* git rm `filename` - Remove a file from the git repository but leave it as a local file. 
* git mv `old_filename` `new_filename` - Useful for renaming a file

Here are some good resources (there are many git commands and a good reference is vital):

* Reference Manual that explains how each git command works: https://git-scm.com/docs
* E-Book for using Git: https://git-scm.com/book/en/v2
* How-to for Un-doing things in Git: https://www.atlassian.com/git/tutorials/undoing-changes

**HEAD**

In Git, the HEAD is the branch you are currently working in. It also can be expressed by saying its the most recent commit in your current branch.  Here is a link to a Git glossary:  https://git-scm.com/docs/gitglossary

**GUI Git Tools**

The "git bash" is a command line that has environment variables setup to make it a little easier to run git.  You can run git from any command line or terminal.  IDE's like IntelliJ (and many others) provide support for git.  If you have git already installed, then the IDE's will run the git commands for you.  However, some problems may not be able to be solved with the IDE.  It is always good to have a basic understanding of how to use git from the command line.   

There are several graphical tools that you can use instead of running git commands or relying on the git integration within the IDE.  Here is a link to several: https://git-scm.com/downloads/guis  You can also use the Github Desktop app:  https://desktop.github.com/ .  When you download Git for Windows, you get Git Cmd (which is an older version of the Git Bash) and Git GUI (which is a simple GUI application ... you will likely enjoy the links above for other more full featured GUI applications).

If you are solving a problem or trying to do branching, having a graphical representation of your branches can be helpful.  The GUI tools above can do this for you.  The GUI will help extensively with merge conflicts and showing diff reports.

Graphical tools do make things a lot easier.  However, if you are using Git extensively or if you are writing scripts that run Git commands, it is much easier to accomplish your task if you are very familiar with the command line tools.  Actually, it is good to be able to both to troubleshoot problems.  You can use any of the above tools including the command line at the same time.

**IntelliJ and Git**

The basic commands of add, commit, pull, and push are all available (including branching) in IntelliJ.  If you get a conflict on a merge, IntelliJ will provide you a tool to manually merge the changes.  

Here are 2 use cases that are common:
* New Project - Create the project new in IntelliJ (or Android Studio) and then select "Import into Version Control" from the VCS menu option.  Select "Share Project on Github".  You will be prompted for details about the repository you want to create in Github.
* Existing Project - Select "Checkout from Version Control", and then select "git", and then specify the Github repository that you want to open.

Once doing one of the above, you will be able to use the git options in IntelliJ instead of the command line (if you want).

Another option besides the menus and button in IntelliJ is to use the Terminal within IntelliJ to run git commands.  You can find the terminal by going to View, Tool Windows, and select Terminal.

**Git Troubleshooting**

If you are running into git problems using IntelliJ, you may want to try to do the actions from the command line.  Rely on "git status" to see what is going on.  If you are on the command line, git will frequently give you hints about what needs to be done to solve the problem.  A common issue is that files can be in one of 3 states: not tracked, tracked but uncommitted, or committed.  Not knowing the state of the file can result in error messages when you try to run commands.  It is good idea to use "git status" to see if you have any not tracked or uncommitted files.

**What can be stored in Git**

Normally, we store code files in git.  However, you can store any file including requirements documents, design diagrams, user guides, and test cases.

Traditionally we put only one project into a Git repository.  If you share the repository into GitHub and you put multiple projects within it, then if someone clones your repository they will receive all the projects.  Additionally IDE's like IntelliJ work better with the one project to one repository model.  Its not impossible to have many, but its easier to checkout a project from GitHub and open in IntelliJ if its just one.

However, just like the class github site for this course, having a single repository to store all artifacts shared with students works well too.

**Git and Other Configuration Tools**

Git is commonly used in industry because its free and supports tools like GitHub (which does cost for companies).  There are many other tools which are not free that offer either specialized and/or simpler interfaces.  Other tools include: Clearcase, CVS, Dimensions, MKS, SubVersion, Enovia, and Mercurial.

One of the benefits of other tools such as Clearcase, MKS, and Enovia is the ability to integrate workflows with problem reports or change requests.  

GitHub provides alot of these same capabilities.  However, editing files in GitHub is not common because there are better editors available for software programmers to use (ex: Visual Studio Code, IntelliJ, Sublime, etc).

**Security**

If you have a public repository, then everyone in the world will see your code.  However, they won't be able to change your code.  They can clone your repository and change it in their own repository.

If you want to keep your code secure, put it into a private repository.  You CS246 android app should be put into a private repository with your entire team and the teacher as collaborators.

**Branching & Merging**

It is not necessary to do branching for the CS246 team project.  It was presented in the material this week so you were aware of it.  For your first big project in GitHub with others, its okay to all merge to master.  You will most likely just need to add, commit, pull, and push in the master branch of the repository on your hard drive.  You will then push into the master repository in GitHub.

Branching, Cloning and Committing are different.  A Clone is when you make a copy of GitHub repository on your computer. A commit is used when you make a change in your repository.    A Branch is a place that you make a change.  You can commit either on the Master branch or on a separate branch that you have previous created.  When you think about branching, you can think about a tree data structure that git is maintaining for you.  Each node in the tree represents a version of the repository that git is keeping track of.

Branching is useful in the following scenarios:

* Usually each software bug or new feature is assigned a change control ID so that the team can control changes made to the software.  In this type of environment, a separate branch would be created for each approved change.  Once the change is working, the entire branch can be pushed to Github.  The owner of the GitHub repository can approve the branch (called a pull request) and merge it into GitHub.
* If you wanted to prototype an idea, you could create a branch to work on the idea separate from the rest of the project.  Occasionally, you would rebase to keep your mini-project in synch with the master branch.  If desired, the prototype idea could be merged into the master.

Usually you create a branch from the HEAD of the current branch you are on.  However, it is possible to create a branch from a previous commit.  You will need the SHA1 hash name of the commit to do this.  See  https://git-scm.com/docs/git-branch for more information about this option.

When you work with a branch, these are common commands
* git branch -a - Show all branches
* git branch -d my_branch - Delete "my_branch".  Do this only after merging it to the master branch
* git branch my_branch - Create a branch called "my_branch"
* git checkout my_branch - Move to the "my_branch" branch.  Files in your hard drive will physically move around when you do this.  Use branch "master" to go back to the master branch.
* git rebase master - While you are in your branch, you can issue this command to re-synch your branch with the master.  This will merge in commits that were put into the master branch since you originally created your branch (or since you last did a rebase).  Git does this by un-doing (or rewinding) all the commits you made on your branch, then it uses the current master (re-bases), and finally re-applies all the commits you made previously.  Conflicts may occur that you will need to resolve.
* git merge my_branch - While you are in the master branch, you can merge you final changes from the "my_branch" branch.  Only do this when are finished with your branch.  Conflicts may occur if you haven't previously done a rebase.
* git push -u origin my_branch - Used to push a branch into GitHub

The GUI tools can be helpful in working with branches because its easier to move between branches and you can see the effects of merging graphically.

Here is a link to more advanced topics of branching:  https://git-scm.com/book/en/v2/Git-Tools-Advanced-Merging

Whenever you do a "git checkout" you are changing the HEAD.  Normally you specify a branch name (or master).  Its possible to checkout a commit which is called a detached HEAD.  This was done in the tutorial to show you how the HEAD can move around.  This is not something you will usually need to do.

Merging also occurs when you do a pull from GitHub.  If you changed and committed a file and someone else committed the same file, you will see their file during a pull.  GitHub will do one of 2 things:

* If the change is in different parts of the file, then Git will automatically merge the changes.  You need to be wary of this because someone's change may affect your change.  After you pull, if changes were merged, it might be a good idea to test your code before you do the push.
* If the change is in the same lines that someone else changed, then Git will require you to manually merge the changes.  This will also occur if two people create the same file but with different contents.  If you are using IntelliJ, you can do the merge using the diff tool that will show you both changes.  You will want to work together with the other team member when doing the manual merge.  You will have to commit your merged changes and then push.  Again, it would be wise to re-test before your push.  

It is wise to communicate with the team before you push so they know what to expect as they are working on their code.

**General Daily Use of GitHub**

You should follow the general rules for daily use of GitHub:
1. Start your day with a pull.  This assumes you did the last step below at the end of your previous day (commit all changes).  You will receive any changes your teammates have made since last you worked.  Resolve any conflicts that may occur.
2. Add and Commit your changes frequently during the day especially after you test and you make incremental progress.
3. Once your code is working and ready to be given to your team, then do a pull to bring in any additional changes and resolve any conflicts.  Test one more time and then push your changes.  Notify your team that you pushed changes.  You should push code frequently to your team but always (always) make sure it works before you do so.
4. At the end of the day, make sure you have committed all your changes. 

Notice that the push is only done after you have done testing of your code and integrated with changes made by others (the pull and testing in step 3).  Be nice to your team and make sure you only push code that works.

**Making a Portfolio in GitHub**

You should begin to put your code creations into GitHub to build your own Personal Software Portfolio.  As a general rule, one project per repository.  Some guidelines:

* Code that solves specific problem sets from your college classes should be put in a *PRIVATE* repository so as not to share solutions with others.
* Code that you created on your own from your own design or problem sets should be considered for a *PUBLIC* repository.  Projects where you were not solving the same problem as everyone else (like your CS246 Team App Project) are great to put in a *PUBLIC* repository.
* Each repository should include a readme.md file that includes documentation written in the markdown language (https://www.markdownguide.org/).  Provide details about your project and where you went to get information.  Include some screenshots of you software running.
* Especially for public repositories that contain you personal coding projects, put each project (each piece of software) into its own separate repository with documentation.
* Provide tags and overviews of your repositories in GitHub.
* Ensure you use a .gitignore file to hide any private keys

**Connecting Git with GitHub**

If you have existing code that has not been published to GitHub yet, the best thing to do is use the tools in your IDE (e.g. IntelliJ) to publish to the code to GitHub.  This will both create the GitHub repository but also ensure the right things are published.

Other scenarios:

To clone a repository already established in GitHub: 

    git clone <url from github>

To connect your repository on your hard drive to one created on GitHub (do this if you aren't using a tool like IntelliJ that has a Publish to GitHub feature):

    git remote add origin <url from github>
    git push -u origin master

To allow other people to modify your repository, you need to add them as a collaborator.  A collaborator will receive an email from GitHub inviting them to join the project.

**Conflicts**

Conflicts can occur if two team members make changes to the same code file.

When you do any merge (either because of a pull, rebase, or merge), git will attempt to merge the changes automatically.  If the same lines of a file were modified by multiple people, a merge conflict will occur.  Git will give you a message that a conflict exists.  When you look in the code, you will see the following type of text:

    <<<<<<< HEAD
    System.out.println("Hello Universe!");
    =======
    System.out.println("Hello World!");
    >>>>>>> test

The first println was from the HEAD (We were trying to merge into this).  The other came from another branch (in this the "test" branch ... or it could be from GitHub).  You need to manually modify these lines of code (removing the <<< >>> ==== lines as well) to show the correct code you want.  This may require that you work together with you team to determine the correct code.

If you are using an IDE tool like IntelliJ, this process is made easier by using graphical interfaces to both see the changes and select the code that you want to merge together.

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

When you use Android Studio and IntelliJ (along with other tools), the tool will frequently create a default .gitignore for you.  When you use a tool, you should let the tool generate the .gitignore for you.

**Threading**

Threading is used whenever you want to concurrently do 2 or more things in software.  Possible scenarios include:

- Perform multiple tasks at once
- Multiple requests are coming in and you want to process them without preventing more requests from arriving.  This is very common with a client/server architecture.
- You don't want to delay the user from doing something else.  Often we describe this a doing something in background.
- You are responding to multiple events triggered by the user or from other software.  Event driven programming like this is very common.  A thread is started when the event happens.

To create a thread in Java, you can either:

* "extends Thread" - We do this when we don't have another base class that we need to extend
* "implements Runnable" - We do this if we already have a base class that is extended

Both of these classes do the same thing but need to be started differently:

```java
public class Work1 extends Thread {
    public void run() {
        System.out.println("Working in the background");
    }
    
    public static void main(String []args) {
        Work1 work = new Work1();
        work.start(); // Creates & starts the thread and calls the "run" function
    }
}

public class Work2 implements Runnable {
    public void run() {
        System.out.println("Working in the background");
    }
    
    public static void main(String []args) {
        Work2 work = new Work(2);
        Thread thread = new Thread (work); // Creates thread
        thread.start(); // Starts the thread and calls the "run" function
    }
}

```

**Team Conflict and Communication**

It is very common that not everyone will agree on something in a team.  When this happens, we first try to each share our opinion and find either agreement or find compromise.  Sometimes it takes an individual to agree to be okay with an idea even if its not what they would have picked but agree that it should work okay.  If no one is willing to agree, then you have to come up with a way to just pick one (flip a coin, play a game, take a vote).  Usually, if two people both think an idea is good, its probably the case that they are both right and you will be fine picking either one (assuming both ideas were ethical).

Communication should be done regularly on a team.  All team members must commit to meeting weekly and communicating via Slack regularly during the week.  If this is not done, projects will run into serious problems.  Communication requires more than 1 person.  This means that if you (and the other team members) are not actively engaged in communication, then it won't happen.  To encourage communication when it is not occurring, you should continue to invite participation.  You should reach out just to say "hi" in addition to talking technically about the project.  You should include your team members in your daily prayers.  They may have other things going on that are causing a lot of stress that you can't help with ... but Heavenly Father can help.  If you are unable to make progress because a team member is not participating, then you should talk to me (the teacher).  The projects are done as a group, but they are individually graded.


