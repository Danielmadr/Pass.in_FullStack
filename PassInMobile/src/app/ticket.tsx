import { Credential } from "@/components/credential";
import { FontAwesome } from "@expo/vector-icons";
import { Header } from "@/components/header";
import {
  StatusBar,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  Alert,
  Modal,
} from "react-native";
import { colors } from "@/styles/colors";
import { Button } from "@/components/button";
import { useState } from "react";
import * as ImagePicker from "expo-image-picker";
import { QRCode } from "@/components/qrcode";

export default function Ticket() {
  const [image, setImage] = useState("");
  const [AmplifyQRCode, setAmplifyQRCode] = useState(false);

  async function handleSelectImage() {
    try {
      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsEditing: true,
        aspect: [4, 4],
        quality: 1,
      });

      if (result.assets) {
        setImage(result.assets[0].uri);
      }
    } catch (error) {
      console.log(error);
      Alert.alert("Atenção", "Falha ao selecionar imagem");
    }
  }

  return (
    <View className="flex-1 bg-green-500">
      <StatusBar barStyle="light-content" />
      <Header title="Minha Credencial" />

      <ScrollView
        className="-mt-28 -z-10"
        showsVerticalScrollIndicator={false}
        contentContainerClassName="px-8 pb-8"
      >
        <Credential
          image={image}
          onChangeAvatar={handleSelectImage}
          onAmplifyQRCode={() => setAmplifyQRCode(true)}
        />

        <FontAwesome
          name="angle-double-down"
          size={24}
          color={colors.gray[300]}
          className="self-center my-6"
        />

        <Text className="text-white font-bold text-2xl mt-4">
          Compartilhar credencial
        </Text>

        <Text className="text-white font-regular text-base mt-1 mb-6">
          Mostre ao mundo que você vai participar do Unite Summit!
        </Text>

        <Button title="Compartilhar" />

        <TouchableOpacity activeOpacity={0.7} className="mt-10">
          <Text className="text-white text-base font-bold text-center">
            Remover Ingresso
          </Text>
        </TouchableOpacity>
      </ScrollView>

      <Modal visible={AmplifyQRCode} statusBarTranslucent>
        <View className="flex-1 bg-green-500 items-center justify-center">
          <QRCode value="123456" size={300} />
        </View>
        <TouchableOpacity
          activeOpacity={0.7}
          onPress={() => setAmplifyQRCode(false)}
          className="w-full h-10 bg-white justify-center"
        >
          <Text className="text-green-500 text-base font-bold text-center">
            Fechar
          </Text>
        </TouchableOpacity>
      </Modal>
    </View>
  );
}
