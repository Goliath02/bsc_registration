<script setup>
const modelValue = defineModel();
</script>

<template>

	<div class="flex gap-[0.5em]">
		<input v-model="modelValue" name="datenschutzCheckbox" id="datenschutzCheckbox" type="checkbox" class="min-w-[1.5em] min-h-[1.5em]">

		<label for="datenschutzCheckbox" class="text-white font-bold text-[1.2em]">
			<!--      //TODO change a tag into vue view link to route to "Datenschutz" page -->
			<p>Hiermit stimme ich den
				<router-link to="/datenschutz" class="text-blue-600 underline">Datenschutzrichtlinien</router-link>
				zu.
			</p>
		</label>

	</div>

</template>

<style scoped>

</style>