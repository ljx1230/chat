<template>
    <ContentField>
        <ChatComponent></ChatComponent>
    </ContentField>
</template>

<script>
import ChatComponent from '@/components/ChatComponent.vue';
import ContentField from '@/components/ContentField.vue';
export default {
    name: "ChatView",
    components: {
        ChatComponent,
        ContentField,
    }
}
</script>

<style scoped>

</style>