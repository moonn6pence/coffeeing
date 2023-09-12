/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      spacing: {
        // capsule card 용 w, h
        70.5: '17.625rem',
        94: '23.5rem',
      },
      colors: {
        'font-gray': '#4D4D4D',
      },
    },
  },
  plugins: [],
};
