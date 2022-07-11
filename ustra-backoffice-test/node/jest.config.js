const { defaults } = require('jest-config')

module.exports = {
  transform: {
    '^.+\\.ts$': 'ts-jest',
  },
  testRegex: '\\.test\\.ts$',
  moduleFileExtensions: [...defaults.moduleFileExtensions, 'ts', 'js'],
  globals: {
    'ts-jest': {
      diagnostics: true,
    },
  },
}
