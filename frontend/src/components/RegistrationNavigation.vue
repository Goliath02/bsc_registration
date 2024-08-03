<script>
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";

export default {
	name: "RegistrationNavigation",

	data() {
		return {
			pages: [DefaultRegistration, FinanzialRegistration],
			activePage: {}
		}
	},
	computed: {

		isFirstPage() {
			return this.$route.name === this.pages[0].name;
		},

		isLastPage() {
			return this.$route.name === this.pages[this.pages.length - 1].name;
		},

		getLastPageButtonText() {
			return this.$route.name === this.pages[this.pages.length - 1].name ? "Abschicken" : "Weiter";
		}

	}

}
</script>

<template>

	<div class="flex p-[1em]">

		<div :class="{ 'invisible': isFirstPage}" class="basis-1/3 ">
			<router-link to="/">
				<button class="font-bold  px-[1em] py-[0.5em] rounded">Zurück</button>
			</router-link>
		</div>

		<div class="flex basis-1/3 justify-center items-center gap-[1em]">
			<div v-for="page in pages" :class="{'!bg-white' : $route.name === page.name}"
			     class="w-[0.8em] h-[0.8em] bg-stone-500 rounded-full !transition-colors hover:bg-red-600"></div>
		</div>

		<div class="basis-1/3 flex justify-end">
			<router-link v-if="!isLastPage" to="/kontodaten">
				<button class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded">{{ getLastPageButtonText }}</button>
			</router-link>
			<button v-else class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded">{{ getLastPageButtonText }}</button>

		</div>

	</div>
</template>

<style scoped>

</style>