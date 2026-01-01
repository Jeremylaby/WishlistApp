const common = {
  borderRadius: {
    sm: '4px',
    md: '8px',
    lg: '12px',
    xl: '16px',
  },
  borderWidth: {
    thin: '1px',
    normal: '2px',
    thick: '4px',
  },
  breakpoints: {
    xs: '480px',
    sm: '576px',
    md: '768px',
    lg: '1024px',
    xl: '1280px',
  },
  buttonPadding: {
    sm: '6px 15px',
    md: '10px 40px',
    lg: '14px 60px',
  },
  shadows: {
    sm: '0px 1px 2px rgba(0, 0, 0, 0.1)',
    md: '0px 2px 5px rgba(0, 0, 0, 0.3)',
    lg: '0px 10px 15px -3px rgba(0, 0, 0, 0.6)',
  },
  typography: {
    fontFamily: 'Inter, sans-serif',
    fontSize: {
      xxs: '0.5rem',
      xs: '0.75rem',
      sm: '0.875rem',
      md: '1rem',
      lg: '1.25rem',
      xl: '1.5rem',
      xxl: '2rem',
    },
    fontWeight: {
      thin: 200,
      regular: 400,
      medium: 500,
      bold: 700,
    },
  },
};

const mauveNeutrals = {
  100: '#14101A',
  200: '#1A1422',
  300: '#241B2E',
  400: '#32263E',
  500: '#43344F',
  600: '#5E516A',
  700: '#7C6F88',
  800: '#C9C1D1',
  900: '#F7F2F8',
};

export const darkTheme = {
  ...common,
  colors: {
    white: '#FFFFFF',
    black: '#000000',
    background: '#232323',
    text: '#1A1A1A',
    textMuted: '#B6A9B3',

    neutrals: mauveNeutrals,

    primary: {
      100: '#FFF1F2',
      200: '#FFE4E6',
      300: '#FECDD3',
      400: '#FDA4AF',
      500: '#FB7185',
      600: '#F43F5E',
      700: '#E11D48',
      800: '#BE123C',
      900: '#881337',
    },

    secondary: {
      100: '#FFF8E1',
      200: '#FFECB3',
      300: '#FFE08A',
      400: '#FFD257',
      500: '#F4C542',
      600: '#D8A72D',
      700: '#B8860B',
      800: '#8A5A00',
      900: '#5A3A00',
    },

    danger: {
      100: '#2A0E0E',
      200: '#3A1212',
      300: '#4A1616',
      400: '#7F1D1D',
      500: '#B91C1C',
      600: '#DC2626',
      700: '#EF4444',
      800: '#F87171',
      900: '#FCA5A5',
    },

    success: {
      100: '#062013',
      200: '#07301A',
      300: '#0B3F23',
      400: '#14532D',
      500: '#16A34A',
      600: '#10B981',
      700: '#22C55E',
      800: '#34D399',
      900: '#86EFAC',
    },

    warning: {
      100: '#1F1307',
      200: '#2A1708',
      300: '#3A1F0B',
      400: '#7C2D12',
      500: '#C2410C',
      600: '#EA580C',
      700: '#F97316',
      800: '#FB923C',
      900: '#FDBA74',
    },

    info: {
      100: '#071A24',
      200: '#0B2431',
      300: '#0F2F40',
      400: '#0C4A6E',
      500: '#15506B',
      600: '#0369A1',
      700: '#0284C7',
      800: '#0EA5E9',
      900: '#38BDF8',
    },
  },
};

export const lightTheme = {
  ...common,
  colors: {
    white: '#FFFFFF',
    black: '#000000',
    background: '#FFFFFF',
    text: '#1A1A1A',
    primary: {
      100: '#EBFCFF',
      200: '#D0F1F7',
      300: '#B8E9F2',
      400: '#9BDDEB',
      500: '#7ED3E4',
      600: '#54ADBF',
      700: '#308899',
      800: '#176373',
      900: '#08414D',
    },
    neutrals: {
      100: '#F5F7F9',
      200: '#E4E8EB',
      300: '#D1D6DB',
      400: '#B0B7BE',
      500: '#8D96A0',
      600: '#6A737C',
      700: '#4B5563',
      800: '#2F3841',
      900: '#1F2937',
    },
    danger: {
      100: '#FFEBEB',
      200: '#FBC1C1',
      300: '#F79898',
      400: '#F36E6E',
      500: '#EF4444',
      600: '#C63434',
      700: '#9D2323',
      800: '#741212',
      900: '#4B0202',
    },
    success: {
      100: '#ECFDF5',
      200: '#D1FAE5',
      300: '#A7F3D0',
      400: '#6EE7B7',
      500: '#34D399',
      600: '#10B981',
      700: '#059669',
      800: '#047857',
      900: '#065F46',
    },
    warning: {
      100: '#FFFBEB',
      200: '#FEF3C7',
      300: '#FDE68A',
      400: '#FCD34D',
      500: '#FACC15',
      600: '#EFAF1C',
      700: '#E49124',
      800: '#D9742B',
      900: '#A25720',
    },
    progress: {
      100: '#FF4B4B', // red
      200: '#FF8C42', // orange
      300: '#FFD93D', // yellow
      400: '#B7E43F', // lime
      500: '#4CAF50', // green
    },
  },
};
