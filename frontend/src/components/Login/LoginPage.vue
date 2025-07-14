<script setup lang="ts">
import {ref} from "vue";
import {Button, InputText} from "primevue";
import {useAuthStore} from '@/stores/authStore'
import Bsc_header from "@/components/BSC_Header.vue";

const email = ref('')
const password = ref('')
const auth = useAuthStore()

const loading = ref(false);
const logInFailed = ref(false);

async function handleLogin() {

    loading.value = true;

    auth.login(email.value, password.value).then()
	    .catch(() => {
            logInFailed.value = true;
	    })
	    .finally(() => {
            loading.value = false;
	    })
}

</script>

<template>
	<Bsc_header/>
	<div class="bg-surface-50 dark:bg-surface-950 px-6 py-20 md:px-12 lg:px-20">
		<div
				class="bg-surface-0 dark:bg-surface-900 p-8 md:p-12 shadow-sm rounded-2xl w-full max-w-xl mx-auto flex flex-col gap-8"
		>
			<div class="flex flex-col items-center gap-4">
				<div class="flex items-center gap-4">
					<img class="h-24" src="@/assets/logo.svg" alt="logo"/>
				</div>
				<div class="flex flex-col items-center gap-2 w-full">
					<div
							class="text-surface-900 dark:text-surface-0 text-2xl font-semibold leading-tight text-center w-full"
					>
						Login
					</div>
					<!--          <div class="text-center w-full">-->
					<!--            <span class="text-surface-700 dark:text-surface-200 leading-normal"-->
					<!--              >Don't have an account?</span-->
					<!--            >-->
					<!--            <a-->
					<!--              class="text-primary font-medium ml-1 cursor-pointer hover:text-primary-emphasis"-->
					<!--              >Create today!</a-->
					<!--            >-->
					<!--          </div>-->
				</div>
			</div>
			<div class="flex flex-col gap-6 w-full">
				<div class="flex flex-col gap-2 w-full">
					<label
							for="email1"
							class="text-surface-900 dark:text-surface-0 font-medium leading-normal"
					>Email Address</label
					>
					<InputText
							v-model="email"
							id="email1"
							type="text"
							placeholder="Email address"
							class="w-full px-3 py-2 shadow-sm rounded-lg"
							@keyup.enter="handleLogin"
					/>
				</div>
				<div class="flex flex-col gap-2 w-full">
					<label
							for="password1"
							class="text-surface-900 dark:text-surface-0 font-medium leading-normal"
					>Password</label
					>
					<InputText
							v-model="password"
							id="password1"
							type="password"
							placeholder="Password"
							class="w-full px-3 py-2 shadow-sm rounded-lg"
							@keyup.enter="handleLogin"
					/>
				</div>
				<div
						class="flex flex-col sm:flex-row items-start sm:items-center justify-between w-full gap-3 sm:gap-0"
				>
					<div class="flex items-center gap-2">
					</div>

					<div class="w-full h-5">
						<Message v-if="logInFailed" class="w-full" severity="error">Login failed, please check your credentials</Message>
					</div>
				</div>
			</div>
			<Button
					label="Sign In"
					icon="pi pi-user"
					class="w-full py-2 rounded-lg flex justify-center items-center gap-2"
					@click="handleLogin"
			>
				<ProgressSpinner v-if="loading"
				                 style="width: 1.5em; height: 1.5em;
	                                --p-progressspinner-color-one: #fff;
	                                --p-progressspinner-color-two: #fff;
	                                --p-progressspinner-color-three: #fff;
	                                --p-progressspinner-color-four: #fff;"
				                 strokeWidth="8" animationDuration="1.5s"/>
				<div v-else class="flex items-center gap-2">
					<i class="pi pi-user !text-base !leading-normal"/>
					<p>Log in</p>
				</div>
			</Button>
		</div>
	</div>
</template>

<style scoped></style>