import {fileURLToPath, URL} from "node:url";
import tailwindcss from '@tailwindcss/vite'
import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import Components from 'unplugin-vue-components/vite';
import {PrimeVueResolver} from '@primevue/auto-import-resolver';

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(),
        tailwindcss(),
        Components({
            resolvers: [
                PrimeVueResolver()
            ]
        })
    ],
    server:{
        proxy: {
            '/api': {
                target: 'http://localhost:8080/',
                changeOrigin: true, // Ensure the request appears to come from the frontend server
                rewrite: (path) => path.replace(/^\/api/, ''), // Optional: Remove '/api' prefix
            },
        }
    },
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
});
