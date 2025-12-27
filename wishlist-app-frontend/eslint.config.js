import js from '@eslint/js'
import globals from 'globals'
import tseslint from 'typescript-eslint'
import pluginReact from 'eslint-plugin-react'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import importPlugin from 'eslint-plugin-import'
import prettier from 'eslint-config-prettier'
import { defineConfig, globalIgnores } from 'eslint/config'

export default defineConfig([
    globalIgnores(['dist', 'eslint.config.js']),

    // ... ewentualny blok JS + React ...

    {
        name: 'TypeScript + React Rules',
        files: ['**/*.{ts,tsx}'],
        languageOptions: {
            parser: tseslint.parser,
            parserOptions: {
                project: ['./tsconfig.app.json', './tsconfig.node.json'],
                ecmaVersion: 'latest',
                sourceType: 'module',
                ecmaFeatures: {
                    jsx: true,
                    modules: true,
                },
            },
            globals: globals.browser,
        },
        plugins: {
            // UWAGA: NIE definiujemy tu już 'react-refresh'
            'typescript-eslint': tseslint,
            react: pluginReact,
            'react-hooks': reactHooks,
            import: importPlugin,
        },
        extends: [
            // TS
            ...tseslint.configs.recommended,
            // importy dla TS
            importPlugin.configs.typescript,
            // Hooks – flat config
            reactHooks.configs.flat.recommended,
            // Vite + React Refresh – TU wchodzi plugin react-refresh
            reactRefresh.configs.vite,
        ],
        rules: {
            // React
            ...pluginReact.configs.recommended.rules,
            ...pluginReact.configs['jsx-runtime'].rules,
            // Hooks
            ...reactHooks.configs['recommended-latest'].rules,
            // Importy
            ...importPlugin.configs.recommended.rules,
        },
        settings: {
            react: {
                version: 'detect',
            },
            'import/resolver': {
                typescript: {
                    project: './tsconfig.app.json',
                },
                node: true,
            },
        },
    },

    // Na końcu wyciszamy reguły kolidujące z Prettierem
    prettier,
])
