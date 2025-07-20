<script setup lang="ts">

import {formatDate} from "@/utils/dateUtil";

const startDate = defineModel<Date>({type: Date});

</script>

<template>
	<div>
		<div class="flex gap-4">
			<div>
				<div>From:</div>
				<div v-if="startDate != undefined">{{ formatDate(startDate) }}</div>
			</div>

			<div>
				<div>To:</div>
				<div v-if="startDate != undefined">{{ formatDate(startDate) }}</div>
			</div>
		</div>
	</div>
</template>

<style scoped>

</style>