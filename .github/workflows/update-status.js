const { Octokit } = require('@octokit/rest');

async function updatePRStatus() {
  const token = process.env.GITHUB_TOKEN;
  const owner = process.env.GITHUB_REPOSITORY.split("/")[0];
  const repo = process.env.GITHUB_REPOSITORY.split("/")[1];
  const sha = process.env.GITHUB_SHA;

  const octokit = new Octokit({
    auth: token,
  });

  const { data: statuses } = await octokit.repos.listCommitStatusesForRef({
    owner,
    repo,
    ref: sha,
  });

  const testsPassed = statuses.every((status) => status.state === 'success');

  if (testsPassed) {
    console.log('Tests passed. PR is ready to be merged.');
    process.exit(0);
  } else {
    console.log('Tests failed. PR cannot be merged.');
    process.exit(1);
  }
}

updatePRStatus();
