import 'styled-components';
import { darkTheme } from './theme/index.ts';

type AppTheme = typeof darkTheme;

declare module 'styled-components' {
  /* eslint-disable @typescript-eslint/no-empty-object-type */
  export interface DefaultTheme extends AppTheme {}
}
