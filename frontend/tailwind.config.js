/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
    ],
    theme: {

        extend: {
            colors: {
                'bsc-gray': '#222222'
            }
        },
    },
    plugins: [],
}

