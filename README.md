<p align="center">
  <a href="https://jimlyas.github.io/arc/">
    <img src="https://raw.githubusercontent.com/jimlyas/arc/main/docs/src/assets/arc_logo.png" width="200" alt="ARC">
  </a>
</p>

<p align="center">
  <img alt="GitHub commit activity" src="https://img.shields.io/github/commit-activity/m/jimlyas/arc">
  <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/jimlyas/arc">
  <img alt="GitHub" src="https://img.shields.io/github/license/jimlyas/arc">
  <img alt="GitHub" src="https://img.shields.io/badge/android%20sdk-31-yellow">
  <img alt="GitHub Workflow Status (with branch)" src="https://img.shields.io/github/actions/workflow/status/jimlyas/arc/documentation.yml"><br/>
  <strong>The only Android library, your project probably ever need</strong>
</p>

## Motivations

I started this project as a hobby, beside improving my skill as a developer but to see how can I
improve my experience in developing Android application.

We already know every time we start a new project, we usually take some time to setup the project
with something like:

- Adding necessary dependencies to the project, where we usually already know what dependencies to
  use based on the requirements of current project and our experience from past project, so
  basically a simple copy paste will do the trick
- Before any feature implementation, building the base class for things like `Activity`,
  `Fragment`, and perhaps `RecyclerView` adapter. All of those things and implement any
  functionalities that we think might be useful later so we don't have to write it over and over
  again.
- Share your initial increments to other developers, perhaps you do things differently one way and
  use different libraries so you communication at the start of project become very important

Basically what I want to achieve is to reduce all of those repetitive tasks when starting new
project so I can start just building the feature as soon as possible.

I know that you can already do that using boilerplate where you run a command in terminal and by
inputting some parameters, you can get ready to use project with all dependencies and setup provided
for you. But that's just one way ticket, and you can't get any improvements if there's new
tech/libraries come along. So I want to do that by building a library / package / dependency that
you can gradually use, and you can just update the version when there's new feature or bugfix.

## License

```
MIT License

Copyright © 2022-2023 Jimly Asshiddiqy

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
IN THE SOFTWARE.
```