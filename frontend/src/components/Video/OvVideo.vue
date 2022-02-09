<template>
	<video autoplay />
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
	watch: {
		streamManager: function () {
			this.streamManager.addVideoElement(this.$el);
		}
	}
};
</script>
