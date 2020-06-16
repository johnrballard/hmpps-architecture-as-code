# hmpps-architecture-as-code

Modelling architecture in HM Prisons and Probations Service (HMPPS) with the [C4 model][c4] and [Structurizr][structurizr].

## Workspaces

This repository defines two workspaces:

- [Custody][workspace-custody], for systems and people related to prisons
- [Rehabilitation][workspace-rehabilitation], for systems and people related to probation

:warning: There is no workspace linking in the Structurizr library yet (as of 16/June/2020); so there is some duplication.

## Running

The CLI has two operations:

1. **Required** Choose the workspace with `--custody` or `--rehabilitation`
1. By default, the CLI writes the workspace to a JSON file. Enable pushing to the workspace with `--push`.

## Secrets

The API requires a workspace-specific API key and secret:
```
STRUCTURIZR_API_KEY=key STRUCTURIZR_API_SECRET=secret ./gradlew run --args='--rehabilitation --push'
```

You can view these secrets on the [dashboard](https://structurizr.com/dashboard), after clicking *Show more...* next to
the desired workspace.



[c4]: https://c4model.com/
[structurizr]: https://structurizr.com/
[workspace-custody]: https://structurizr.com/share/55246
[workspace-rehabilitation]: https://structurizr.com/share/54669