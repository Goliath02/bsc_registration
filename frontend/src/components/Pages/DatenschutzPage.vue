<script setup>

import RegistrationNavigation from "@/components/RegistrationNavigation.vue";
import BSCBanner from "@/components/BSCBanner.vue";
</script>

<template>
	<main class="flex flex-1 justify-center items-center  lg:px-[7%] lg:py-[2%] min-h-0 ">
		<div class="w-full h-full bg-bsc-gray rounded-lg overflow-hidden flex min-h-0">


			<b-s-c-banner class="-lg:hidden"></b-s-c-banner>

			<div class=" text-white flex-1 overflow-y-hidden flex flex-col">

				<router-view></router-view>

				<registration-navigation class="sticky bottom-0 bg-bsc-gray"/>

			</div>
		</div>
	</main>
</template>

<style scoped>

</style>