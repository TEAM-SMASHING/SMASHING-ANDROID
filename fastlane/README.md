fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android qa

```sh
[bundle exec] fastlane android qa
```

Build and deploy a new QA build to Firebase App Distribution (build: debug | release)

### android release

```sh
[bundle exec] fastlane android release
```

Upload Release AAB to Google Play Store (track: internal | beta | production, status: draft | completed | inProgress)

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
