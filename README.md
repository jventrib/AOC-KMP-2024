Quick and dirty tutorial:

make a new private repository on GitHub, put your input data in there

in your main repo, delete all your input data

in your main repo, where your input data was, run this command:
git submodule add <URL to your private input data repo>

optionally rebase your solutions repo to remove the public listing of old commits

force push your solutions repo

Any time you clone your solutions repo elsewhere, you have to do this:

git clone <URL to solutions repo>

git submodule init

git submodule update

