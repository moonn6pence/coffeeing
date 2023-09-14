/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      spacing: {
        // capsule card 용 w, h
        70.5: '17.625rem',
        94: '23.5rem',
        // navbar 용
        '22px': '22px',
        // 120px
        30: '7.5rem',
      },
      colors: {
        'font-gray': '#4D4D4D',
        'light-roasting': '#B78C5F',
        'cinamon-roasting': '#986E3F',
        light: '#F9F6F0',
        'process-bar':'#F3F6FB',
        'select-img':'#F9F6F0'
      },
      width:{
        // 설문 진행 척도 바 길이
        '560px':'560px'
      },
    },
  },
  plugins: [],
};
